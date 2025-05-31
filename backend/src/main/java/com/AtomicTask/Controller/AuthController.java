package com.AtomicTask.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AtomicTask.DTO.OtpReq;
import com.AtomicTask.DTO.RefreshReq;
import com.AtomicTask.DTO.RefreshResp;
import com.AtomicTask.DTO.SignInReq;
import com.AtomicTask.DTO.SignInResp;
import com.AtomicTask.DTO.SignUpReq;
import com.AtomicTask.DTO.SignUpResp;
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
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	@PostMapping("/send-otp")
	public ResponseEntity<String> generateOtp(@RequestBody OtpReq req){
		try {
			return email.sendOtp(req);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Otp sending failed");
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<SignInResp> signin(@RequestBody SignInReq req){
		try {
			return authService.signin(req);
		}catch(Exception e){
			SignInResp resp = new SignInResp();
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			resp.setRefreshToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	

	@PostMapping("/signup")
	public ResponseEntity<SignUpResp> signup(@RequestBody SignUpReq req){
		try {
			return authService.signup(req);
		}catch(Exception e){
			SignUpResp resp = new SignUpResp();
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			resp.setRefreshToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
