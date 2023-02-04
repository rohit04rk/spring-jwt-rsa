package com.mb.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.enums.UserType;
import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.CustomException;
import com.mb.common.model.AuthToken;
import com.mb.common.util.JwtTokenUtil;
import com.mb.common.util.Mapper;
import com.mb.user.dao.UserDao;
import com.mb.user.dto.LoginDto;
import com.mb.user.dto.SignupDto;
import com.mb.user.entity.Role;
import com.mb.user.entity.User;
import com.mb.user.entity.UserRole;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private Mapper mapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private Environment env;

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param signupDto
	 */
	@Transactional
	@Override
	public void userSignup(@Valid SignupDto signupDto) {
		if (userDao.findByEmail(signupDto.getEmail()) != null) {
			throw new CustomException(env.getProperty(ExceptionMessage.USER_ALREADY_EXIST),
					CustomErrorCode.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
		}
		User user = mapper.convert(signupDto, User.class);
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

		Role role = userDao.roleByUserType(UserType.USER);
		List<UserRole> userRoles = new ArrayList<>();
		userRoles.add(new UserRole(role, user));
		user.setUserRoles(userRoles);

		userDao.saveUser(user);
	}

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param loginDto
	 * @return {@link AuthToken}
	 */
	@Override
	public AuthToken userLogin(@Valid LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		return jwtTokenUtil.generateAuthToken(authentication);
	}

}
