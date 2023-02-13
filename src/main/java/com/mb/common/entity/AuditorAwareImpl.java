package com.mb.common.entity;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 * Checks for authentication and returns name from authenticated object else
	 * returns empty string
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link Optional}
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			return Optional.of(authentication.getName());
		}
		return Optional.empty();
	}

}
