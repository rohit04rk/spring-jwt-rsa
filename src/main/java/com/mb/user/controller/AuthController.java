package com.mb.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.common.constant.ResponseMessage;
import com.mb.common.constant.UrlMapping;
import com.mb.common.model.AuthToken;
import com.mb.common.model.SuccessResponse;
import com.mb.common.util.ResponseBuilder;
import com.mb.user.dto.LoginDto;
import com.mb.user.dto.SignupDto;
import com.mb.user.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(UrlMapping.AUTH_BASE_URL)
public class AuthController {

	@Autowired
	private Environment env;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private AuthService authService;

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param signupDto
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(UrlMapping.SIGNUP)
	public ResponseEntity<SuccessResponse<String>> userSignup(@RequestBody @Valid SignupDto signupDto) {

		authService.userSignup(signupDto);

		return responseBuilder.buildSuccessResponse(env.getProperty(ResponseMessage.USER_ADDED_SUCCESS), null,
				HttpStatus.CREATED);
	}

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param loginDto
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(UrlMapping.LOGIN)
	public ResponseEntity<SuccessResponse<AuthToken>> userLogin(@RequestBody @Valid LoginDto loginDto) {

		AuthToken authToken = authService.userLogin(loginDto);

		return responseBuilder.buildSuccessResponse(env.getProperty(ResponseMessage.SUCCESS), authToken,
				HttpStatus.CREATED);
	}
}
