package com.mb.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final HttpStatus httpStatus;

	public CustomException(String message, Throwable e, String errorCode, HttpStatus httpStatus) {
		super(message, e);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public CustomException(String message, String errorCode, HttpStatus httpStatus) {
		super(message);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

}
