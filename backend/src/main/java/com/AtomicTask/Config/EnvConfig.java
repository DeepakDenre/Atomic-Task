package com.AtomicTask.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "env")
public class EnvConfig {
	private String AccessJWTSecret;
	private String RefreshJWTSecret;
	private int AccessTokenExpiration;
	private Long RefreshTokenExpiration;
	private int OtpTimeout;
	public String getAccessJWTSecret() {
		return this.AccessJWTSecret;
	}
	public void setAccessJWTSecret(String AccessJWTSecret) {
		this.AccessJWTSecret = AccessJWTSecret;
	}
	public String getRefreshJWTSecret() {
		return this.RefreshJWTSecret;
	}
	public void setRefreshJWTSecret(String RefreshJWTSecret) {
		this.RefreshJWTSecret = RefreshJWTSecret;
	}
	public int getAccessTokenExpiration() {
		return AccessTokenExpiration;
	}
	public void setAccessTokenExpiration(int tokenExpiration) {
		AccessTokenExpiration = tokenExpiration;
	}
	public int getOtpTimeout() {
		return OtpTimeout;
	}
	public void setOtpTimeout(int otpTimeout) {
		OtpTimeout = otpTimeout;
	}
	public Long getRefreshTokenExpiration() {
		return RefreshTokenExpiration;
	}
	public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
		RefreshTokenExpiration = refreshTokenExpiration;
	}
}
