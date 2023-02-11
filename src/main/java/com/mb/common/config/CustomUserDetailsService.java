package com.mb.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.CustomException;
import com.mb.user.dao.UserDao;
import com.mb.user.entity.User;

@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private Environment env;

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new CustomException(env.getProperty(ExceptionMessage.USER_NOT_FOUND), CustomErrorCode.NOT_FOUND,
					HttpStatus.NOT_FOUND);
		}

		List<SimpleGrantedAuthority> grantedAuthorities = user.getUserRoles().stream()
				.map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getUserType().toString()))
				.toList();

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
}
