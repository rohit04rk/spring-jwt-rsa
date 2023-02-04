package com.mb.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.enums.UserType;
import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.CustomException;
import com.mb.user.entity.Role;
import com.mb.user.entity.User;
import com.mb.user.repo.RoleRepo;
import com.mb.user.repo.UserRepo;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	/**
	 * User by email
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param email
	 * @return {@link User}
	 */
	@Override
	public User findByEmail(String email) {
		try {
			return userRepo.findByEmail(email);
		} catch (Exception e) {
			throw new CustomException(env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e,
					CustomErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Save user
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param user
	 * @return {@link User}
	 */
	@Override
	public User saveUser(User user) {
		try {
			return userRepo.save(user);
		} catch (Exception e) {
			throw new CustomException(env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e,
					CustomErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Role by user type
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userType
	 * @return {@link Role}
	 */
	@Override
	public Role roleByUserType(UserType userType) {
		try {
			return roleRepo.findByUserType(userType)
					.orElseThrow(() -> new CustomException(env.getProperty(ExceptionMessage.USER_ROLE_NOT_FOUND),
							CustomErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			throw new CustomException(env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e,
					CustomErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
