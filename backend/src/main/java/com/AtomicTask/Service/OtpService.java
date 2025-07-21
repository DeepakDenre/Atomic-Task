package com.AtomicTask.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.AtomicTask.Utlity.EncryptionUtils;
import com.AtomicTask.Utlity.JWTUtils;

@Service
public class OtpService {
	
	private int OtpExpiry;
	
	@Autowired
	OtpRepository otpRepo;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	JWTUtils jwt;
	
	@Autowired
	EncryptionUtils ENutil;
	
	public OtpService() {}
	
	@Autowired
	public OtpService(EnvConfig EC) {
		this.OtpExpiry = EC.getOtpTimeout();
	}
	
	
	public String generateOtp(OtpReq req) {
		try {
			String otpString = String.format("%06d", new SecureRandom().nextInt(999999));
			OtpModel otp = new OtpModel();
			otp.setEmail(req.getEmail());
			otp.setOtp(ENutil.encrypt(otpString));
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
				Optional<OtpModel> otp = otpRepo.findById(req.getEmail());
				if(otp.isPresent()) {
					if(req.getOTP().equals(ENutil.decrypt(otp.get().getOtp()))) {
						if(otp.get().getExpiresAt().isAfter(LocalDateTime.now())) {
							UserModel user = userRepo.findByEmail(req.getEmail());
							resp.setResult("Otp Signin Successfull");
							HttpHeaders headers = new HttpHeaders();
							headers.add(HttpHeaders.SET_COOKIE, jwt.generateAccessToken(user).toString());
							headers.add(HttpHeaders.SET_COOKIE, jwt.generateRefreshToken(user).toString());
							// Delete the otp from the database after validation as the user now whas the Access and refresh token for auth
							otpRepo.deleteById(req.getEmail());
							return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
						}else {
							resp.setResult("Expired Otp");
							return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
						}
					}else {
						resp.setResult("Invalid Otp");
						return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
					}
				}else {
					resp.setResult("generate otp first");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setResult("signInMethod is not otp");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignInResp resp = new SignInResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
