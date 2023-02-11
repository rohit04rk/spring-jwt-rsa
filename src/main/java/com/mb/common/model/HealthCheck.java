package com.mb.common.model;

import lombok.Data;

@Data
public class HealthCheck {

	private String appName;

	private String appVersion;

	private String groupId;

	private String artifactId;

}
