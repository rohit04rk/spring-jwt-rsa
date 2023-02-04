package com.mb.common.util;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.FieldError;
import com.mb.common.model.ErrorResponse;
import com.mb.common.model.SuccessResponse;

@Component
public class ResponseBuilder {

	/**
	 * Build http succcess response entity
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <T>
	 * @param message
	 * @param data
	 * @param httpStatus
	 * @return {@link ResponseEntity}
	 */
	public <T> ResponseEntity<SuccessResponse<T>> buildSuccessResponse(String message, T data, HttpStatus httpStatus) {

		SuccessResponse<T> response = new SuccessResponse<>();
		response.setCode(httpStatus.value());
		response.setData(data);
		response.setMessage(message);
		response.setTimestamp(new Date());

		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * Build http error response entity
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param errorCode
	 * @param message
	 * @param httpStatus
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<ErrorResponse> buildErrorResponse(String errorCode, String message, HttpStatus httpStatus) {

		ErrorResponse response = new ErrorResponse();
		response.setStatus(httpStatus.value());
		response.setError(errorCode);
		response.setMessage(message);
		response.setTimestamp(new Date());

		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * Build http validation error response entity
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param message
	 * @param fieldErrors
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<Object> buildErrorResponse(String message, List<FieldError> fieldErrors) {

		ErrorResponse response = new ErrorResponse();
		response.setError(CustomErrorCode.BAD_REQUEST);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(message);
		response.setFieldErrors(fieldErrors);
		response.setTimestamp(new Date());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
