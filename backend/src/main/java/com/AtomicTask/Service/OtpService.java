package com.AtomicTask.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.AtomicTask.Config.EnvConfig;
import com.AtomicTask.DTO.OtpReq;
import com.AtomicTask.DTO.SignInReq;
import com.AtomicTask.DTO.SignInResp;
import com.AtomicTask.Model.OtpModel;
import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.OtpRepository;
import com.AtomicTask.Repository.UserRepository;
import com.AtomicTask.Security.JWTUtils;

@Service
public class OtpService {
	
	private int OtpExpiry;
	
	@Autowired
	OtpRepository otpRepo;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	JWTUtils jwt;
	
	public OtpService() {}
	
	@Autowired
	public OtpService(EnvConfig EC) {
		this.OtpExpiry = EC.getOtpTimeout();
	}
	
	
	public String generateOtp(OtpReq req) {
		try {
			String otpString = String.format("%06d", new Random().nextInt(999999));
			OtpModel otp = new OtpModel();
			otp.setEmail(req.getEmail());
			otp.setOtp(otpString);
			otp.setExpiresAt(LocalDateTime.now().plusMinutes(OtpExpiry));
			otpRepo.save(otp);
			return otpString;
		}catch (Exception e) {
			System.out.println("Otp generation error: "+e);
			return "";
		}
	}
	
	public ResponseEntity<SignInResp> validateOtp(SignInReq req) {
		try {
			SignInResp resp = new SignInResp();
			if("otp".equalsIgnoreCase(req.getSignInMethod())) {
				OtpModel otp = otpRepo.findById(req.getEmail()).get();
				if(otp != null) {
					if(req.getOTP().equalsIgnoreCase(otp.getOtp())) {
						if(otp.getExpiresAt().isAfter(LocalDateTime.now())) {
							UserModel user = userRepo.findByEmail(req.getEmail());
							resp.setMessage("Otp Signin Successfull");
							resp.setToken(jwt.generateToken(user));
							otpRepo.deleteById(req.getEmail());
							return ResponseEntity.status(HttpStatus.OK).body(resp);
						}else {
							resp.setMessage("Expired Otp");
							resp.setToken("");
							return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
						}
					}else {
						resp.setMessage("Invalid Otp");
						resp.setToken("");
						return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
					}
				}else {
					resp.setMessage("generate otp first");
					resp.setToken("");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setMessage("signInMethod is not otp");
				resp.setToken("");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignInResp resp = new SignInResp();
			resp.setMessage("Error: "+e);
			resp.setToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	public void destroyOtp(SignInReq req) {
		try {
			if("otp".equalsIgnoreCase(req.getSignInMethod())) {
				OtpModel otp = otpRepo.findById(req.getEmail()).get();
				if(otp != null) {
					otpRepo.deleteById(req.getEmail());
				}
			}
		}catch (Exception e) {
			System.out.println("Error destroying otp : "+e);
		}
	}
}
