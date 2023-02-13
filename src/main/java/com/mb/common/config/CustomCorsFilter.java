package com.mb.common.config;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter {

	private Logger logger = LogManager.getLogger();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		logger.info("Request  Path {} , Type {} from {}", httpServletRequest.getRequestURI(),
				httpServletRequest.getMethod(), httpServletRequest.getHeader("User-Agent"));

		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, PATCH, OPTIONS, DELETE");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Authorization, authorization, Origin, Content-Type, Version, impersonateUser");
		httpServletResponse.setHeader("Access-Control-Expose-Headers",
				"X-Requested-With, Authorization, authorization, Origin, Content-Type");

		if (!httpServletRequest.getMethod().equals("OPTIONS")) {
			chain.doFilter(request, response);
		} else {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
	}

}
