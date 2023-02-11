package com.mb.common.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.common.constant.JwtConstant;
import com.mb.common.exception.CustomException;
import com.mb.common.model.ErrorResponse;
import com.mb.common.util.JwtTokenUtil;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(JwtConstant.AUTHORIZATION);

		if (header != null && !header.isBlank() && header.startsWith(JwtConstant.BEARER)) {

			String authToken = header.replace(JwtConstant.BEARER, "");
			JWTClaimsSet claimsSet = null;

			try {
				claimsSet = jwtTokenUtil.verifyAndGetTokenClaims(authToken);
				jwtTokenUtil.isJWTExpired(claimsSet.getExpirationTime());
			} catch (CustomException ce) {
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(new ErrorResponse(ce.getMessage(),
						HttpServletResponse.SC_BAD_REQUEST, ce.getErrorCode(), null, new Date())));
				return;
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(claimsSet.getSubject());
			List<SimpleGrantedAuthority> auhtoritites = Arrays
					.stream(claimsSet.getClaim(JwtConstant.AUTHORITIES).toString().split(","))
					.map(SimpleGrantedAuthority::new).toList();

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					"", auhtoritites);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

}
