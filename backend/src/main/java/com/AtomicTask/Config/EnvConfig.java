package com.AtomicTask.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "env")
@Data
public class EnvConfig {
	private String AccessJWTSecret;
	private String RefreshJWTSecret;
	private int AccessTokenExpiration;
	private Long RefreshTokenExpiration;
	private int OtpTimeout;
}
