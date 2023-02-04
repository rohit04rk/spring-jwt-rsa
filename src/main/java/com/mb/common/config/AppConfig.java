package com.mb.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mb.common.constant.SwaggerConstant;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Component
public class AppConfig {

	@Bean
	OpenAPI openApi() {

		return new OpenAPI().info(new Info().title(SwaggerConstant.TITLE).description(SwaggerConstant.DESC)
				.version(SwaggerConstant.VERSION_1));
	}
	
}
