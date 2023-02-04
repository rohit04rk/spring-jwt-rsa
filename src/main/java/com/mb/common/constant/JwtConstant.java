package com.mb.common.constant;

public class JwtConstant {

	private JwtConstant() {
	}

	public static final String BEARER = "Bearer";
	public static final String AUTHORIZATION = "Authorization";
	public static final String ISSUER = "MB";

	public static final String ACCESS_TOKEN_EXPIRY_MS = "access.token.expiry.in.ms";
	public static final String REFRESH_TOKEN_EXPIRY_MS = "refresh.token.expiry.in.ms";
	public static final String RSA_PRIVATE_KEY = "private.key.name";
	public static final String RSA_PUBLIC_KEY = "public.key.name";

	public static final String AUTHORITIES = "authorities";
}
