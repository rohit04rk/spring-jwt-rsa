package com.mb.common.exception;

public class CustomErrorCode {

	private CustomErrorCode() {
	}

	// GENERAL
	public static final String INTERNAL_SERVER_ERROR = "ERR_500";
	public static final String BAD_REQUEST = "ERR_400";
	public static final String NOT_FOUND = "ERR_404";
	public static final String UNAUTHORIZED = "ERR_401";

	// JWT
	public static final String TOKEN_EXPIRED = "TOKEN_001";
	public static final String TOKEN_INVALID = "TOKEN_002";
	public static final String SIGNATURE_INVALID = "TOKEN_003";

	// USER
	public static final String USER_ALREADY_EXISTS = "USER_001";
}
