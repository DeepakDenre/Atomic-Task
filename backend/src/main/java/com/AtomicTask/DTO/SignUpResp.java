package com.AtomicTask.DTO;

public class SignUpResp {
	private String message;
	private String AccessToken;
	private String RefreshToken;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccessToken() {
		return AccessToken;
	}
	public void setAccessToken(String token) {
		AccessToken = token;
	}
	public String getRefreshToken() {
		return RefreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
}
