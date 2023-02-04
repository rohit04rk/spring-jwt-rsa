package com.mb.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthToken {

	private String accessToken;

	private String refreshToken;

	private String tokenType;
}
