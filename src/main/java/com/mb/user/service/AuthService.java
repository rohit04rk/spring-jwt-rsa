package com.mb.user.service;

import com.mb.common.model.AuthToken;
import com.mb.user.dto.LoginDto;
import com.mb.user.dto.SignupDto;

import jakarta.validation.Valid;

public interface AuthService {

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param signupDto
	 */
	void userSignup(@Valid SignupDto signupDto);

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param loginDto
	 * @return {@link AuthToken}
	 */
	AuthToken userLogin(@Valid LoginDto loginDto);

}
