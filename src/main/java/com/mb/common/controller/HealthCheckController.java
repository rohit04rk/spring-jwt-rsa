package com.mb.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.common.constant.KeyConstant;
import com.mb.common.constant.ResponseMessage;
import com.mb.common.constant.UrlMapping;
import com.mb.common.model.HealthCheck;
import com.mb.common.model.SuccessResponse;
import com.mb.common.util.ResponseBuilder;

@RestController
@RequestMapping(UrlMapping.HEALTH_CHECK)
public class HealthCheckController {

	@Autowired
	private Environment env;

	@Autowired
	private ResponseBuilder responseBuilder;

	/**
	 * Health check
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link ResponseEntity}
	 */
	@GetMapping
	public ResponseEntity<SuccessResponse<HealthCheck>> healthCheck() {

		HealthCheck healthCheck = new HealthCheck();
		healthCheck.setAppName(env.getProperty(KeyConstant.APP_NAME));
		healthCheck.setAppVersion(env.getProperty(KeyConstant.APP_VERSION));
		healthCheck.setArtifactId(env.getProperty(KeyConstant.APP_ARTIFACT_ID));
		healthCheck.setGroupId(env.getProperty(KeyConstant.APP_GROUP_ID));

		return responseBuilder.buildSuccessResponse(env.getProperty(ResponseMessage.SUCCESS), healthCheck,
				HttpStatus.OK);
	}

}
