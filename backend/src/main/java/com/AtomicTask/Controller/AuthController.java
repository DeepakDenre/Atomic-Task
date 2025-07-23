package com.AtomicTask.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AtomicTask.DTO.OTP.OtpReq;
import com.AtomicTask.DTO.OTP.OtpResp;
import com.AtomicTask.DTO.Refresh.RefreshReq;
import com.AtomicTask.DTO.Refresh.RefreshResp;
import com.AtomicTask.DTO.SignIn.SignInReq;
import com.AtomicTask.DTO.SignIn.SignInResp;
import com.AtomicTask.DTO.SignUp.SignUpReq;
import com.AtomicTask.DTO.SignUp.SignUpResp;
import com.AtomicTask.Service.AuthService;
import com.AtomicTask.Service.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	EmailService email;

	@PostMapping("/refresh")
	public ResponseEntity<RefreshResp> refresh(@RequestBody RefreshReq req){
		try {
			return authService.refresh(req);
		}catch (Exception e) {
			RefreshResp resp = new RefreshResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

	@PostMapping("/send-otp")
	public ResponseEntity<OtpResp> generateOtp(@RequestBody OtpReq req){
		try {
			return email.sendOtp(req);
		}catch(Exception e){
			OtpResp resp=new OtpResp();
			resp.setMessage("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<SignInResp> signin(@RequestBody SignInReq req){
		try {
			return authService.signin(req);
		}catch(Exception e){
			SignInResp resp = new SignInResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

	@PutMapping("/signup")
	public ResponseEntity<SignUpResp> signup(@RequestBody SignUpReq req){
		try {
			return authService.signup(req);
		}catch(Exception e){
			SignUpResp resp = new SignUpResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
