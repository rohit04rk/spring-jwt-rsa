package com.mb.common.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.common.exception.FieldError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {

	private String message;

	private Integer status;

	private String error;

	private List<FieldError> fieldErrors;

	private Date timestamp;

}
