package com.mb.common.constant;

public class ExceptionMessage {

	private ExceptionMessage() {
	}

	// GENERAL
	public static final String INTERNAL_SERVER_ERROR = "internal.server.error";
	public static final String INVALID_REQUEST_BODY = "invalid.request.body";

	// USER
	public static final String USER_NOT_FOUND = "user.not.found";
	public static final String USER_ROLE_NOT_FOUND = "user.role.not.found";
	public static final String USER_ALREADY_EXIST = "user.already.exist";

	// JWT TOKEN
	public static final String TOKEN_INVALID = "token.invalid";
	public static final String TOKEN_EXPIRED = "token.expired";

}
