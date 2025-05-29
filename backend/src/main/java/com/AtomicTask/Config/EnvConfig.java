package com.AtomicTask.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "env")
public class EnvConfig {
	private String JWTSecret;
	private int TokenExpiration;
	private int OtpTimeout;
	public String getJWTSecret() {
		return JWTSecret;
	}
	public void setJWTSecret(String jWTSecret) {
		JWTSecret = jWTSecret;
	}
	public int getTokenExpiration() {
		return TokenExpiration;
	}
	public void setTokenExpiration(int tokenExpiration) {
		TokenExpiration = tokenExpiration;
	}
	public int getOtpTimeout() {
		return OtpTimeout;
	}
	public void setOtpTimeout(int otpTimeout) {
		OtpTimeout = otpTimeout;
	}
}
