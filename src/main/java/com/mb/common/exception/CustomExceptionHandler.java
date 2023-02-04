package com.mb.common.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.model.ErrorResponse;
import com.mb.common.util.ResponseBuilder;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private Environment env;

	@Autowired
	private ResponseBuilder responseBuilder;

	/**
	 * Custom exception handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param customException
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException customException) {

		return responseBuilder.buildErrorResponse(customException.getErrorCode(), customException.getMessage(),
				customException.getHttpStatus());
	}

	/**
	 * Null pointer exception handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param nullPointerException
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> nullPointerExceptionHandler(NullPointerException nullPointerException) {

		return responseBuilder.buildErrorResponse(CustomErrorCode.INTERNAL_SERVER_ERROR,
				env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Validation exception response handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldErr -> new FieldError(fieldErr.getField(), fieldErr.getDefaultMessage(),
						fieldErr.getRejectedValue()))
				.collect(Collectors.toList());

		return responseBuilder.buildErrorResponse(env.getProperty(ExceptionMessage.INVALID_REQUEST_BODY), fieldErrors);
	}
}
