package com.AtomicTask.DTO;

public class SignInResp {
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
	public void setAccessToken(String AccessToken) {
		this.AccessToken = AccessToken;
	}
	public String getRefreshToken() {
		return RefreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
}
