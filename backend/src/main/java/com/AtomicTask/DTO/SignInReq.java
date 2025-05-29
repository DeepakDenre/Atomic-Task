package com.AtomicTask.DTO;

public class SignInReq {
	private String email;
	private String hashedPassword;
	private String OTP;
	private String SignInMethod;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getSignInMethod() {
		return SignInMethod;
	}
	public void setSignInMethod(String signInMethod) {
		SignInMethod = signInMethod;
	}
}
