package com.mb.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mb.common.entity.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

	/**
	 * Auditor aware bean for jpa auditing
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link AuditorAware}
	 */
	@Bean
	AuditorAware<String> auditorProvider() {

		return new AuditorAwareImpl();
	}

}
