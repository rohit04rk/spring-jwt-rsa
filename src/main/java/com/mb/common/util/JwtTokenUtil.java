package com.mb.common.util;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.constant.JwtConstant;
import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.CustomException;
import com.mb.common.model.AuthToken;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtTokenUtil {

	@Autowired
	private Environment env;

	@Autowired
	private RSAKey rsaKey;

	/**
	 * Generate auth token
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param authentication
	 * @return {@link AuthToken}
	 */
	public AuthToken generateAuthToken(Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		String authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JWTClaimsSet accessTokenClaims = buildJwtClaims(user.getUsername(), authorities,
				Long.valueOf(env.getProperty(JwtConstant.ACCESS_TOKEN_EXPIRY_MS)));
		String accessToken = signedJWT(accessTokenClaims);

		JWTClaimsSet refreshTokenClaims = buildJwtClaims(user.getUsername(), null,
				Long.valueOf(env.getProperty(JwtConstant.REFRESH_TOKEN_EXPIRY_MS)));
		String refreshToken = signedJWT(refreshTokenClaims);

		return new AuthToken(accessToken, refreshToken, JwtConstant.BEARER);
	}

	/**
	 * Verify token and get claims
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param token
	 * @return {@link JWTClaimsSet}
	 */
	public JWTClaimsSet verifyAndGetTokenClaims(String token) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new RSASSAVerifier(rsaKey.toPublicJWK());

			if (!signedJWT.verify(verifier)) {
				throw new CustomException(env.getProperty(ExceptionMessage.SIGNATURE_INVALID),
						CustomErrorCode.SIGNATURE_INVALID, HttpStatus.BAD_REQUEST);
			}

			return signedJWT.getJWTClaimsSet();
		} catch (JOSEException | ParseException e) {
			throw new CustomException(env.getProperty(ExceptionMessage.TOKEN_INVALID), e, CustomErrorCode.TOKEN_INVALID,
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Check if jwt token is expired or not
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param expirationDate
	 * @return {@link Boolean}
	 */
	public void isJWTExpired(Date expirationDate) {
		if (expirationDate.before(new Date())) {
			throw new CustomException(env.getProperty(ExceptionMessage.TOKEN_EXPIRED), CustomErrorCode.TOKEN_EXPIRED,
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Signed jwt
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param claimsSet
	 * @return {@link String}
	 */
	private String signedJWT(JWTClaimsSet claimsSet) {
		try {
			SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), claimsSet);
			signedJWT.sign(new RSASSASigner(rsaKey));

			return signedJWT.serialize();
		} catch (JOSEException e) {
			throw new CustomException(env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e,
					CustomErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Build jwt claims
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param subject
	 * @param authorities
	 * @param milliseconds
	 * @return {@link JWTClaimsSet}
	 */
	private JWTClaimsSet buildJwtClaims(String subject, String authorities, Long milliseconds) {

		return new JWTClaimsSet.Builder().subject(subject).issuer(JwtConstant.ISSUER)
				.claim(JwtConstant.AUTHORITIES, authorities).expirationTime(expiryDate(milliseconds)).build();
	}

	/**
	 * Get expiration date for jwt token
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param milliseconds
	 * @return {@link Date}
	 */
	private Date expiryDate(Long milliseconds) {

		return new Date(System.currentTimeMillis() + milliseconds);
	}
}
