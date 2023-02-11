package com.mb.common.constant;

public class UrlMapping {
	private UrlMapping() {
		throw new IllegalStateException("Constant class.can't instatiate");
	}

	private static final String BASE_URL = "api/v1/";

	// Auth Controller
	public static final String AUTH_BASE_URL = BASE_URL + "auth";
	public static final String SIGNUP = "signup";
	public static final String LOGIN = "login";

	// Health Check Controller
	public static final String HEALTH_CHECK = BASE_URL + "health";

}
