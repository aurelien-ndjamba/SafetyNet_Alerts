package com.safetynets.alerts.api.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="com.safetynets.alerts.api.model")
public class ConfigModel {
	private String dataJson;
}
