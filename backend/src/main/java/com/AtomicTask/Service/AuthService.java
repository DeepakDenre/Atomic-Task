package com.AtomicTask.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;

import com.AtomicTask.DTO.RefreshReq;
import com.AtomicTask.DTO.RefreshResp;
import com.AtomicTask.DTO.SignInReq;
import com.AtomicTask.DTO.SignInResp;
import com.AtomicTask.DTO.SignUpReq;
import com.AtomicTask.DTO.SignUpResp;
import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.UserRepository;
import com.AtomicTask.Utlity.EncryptionUtils;
import com.AtomicTask.Utlity.JWTUtils;


@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JWTUtils jwt;
	
	@Autowired
	OtpService otp;
	
	@Autowired
	EncryptionUtils ENutil;
	
	public ResponseEntity<SignInResp> signin(SignInReq req){
		try {
			SignInResp resp = new SignInResp();
			UserModel user = userRepo.findByEmail(req.getEmail());
			if(user != null) {
				if(req.getSignInMethod() != null) {
					if(req.getSignInMethod().equals("password")) {
						if(ENutil.decrypt(user.getDoubleHashedPassword()).equals(req.getHashedPassword())) {
							HttpHeaders headers = new HttpHeaders();
							headers.add(HttpHeaders.SET_COOKIE, jwt.generateAccessToken(user).toString());
							headers.add(HttpHeaders.SET_COOKIE, jwt.generateRefreshToken(user).toString());
							resp.setResult("password sign in successfull");
							return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
						}else {
							resp.setResult("Invalid credintials");
							return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
						}
					}else if(req.getSignInMethod().equals("otp")) {
						return otp.validateOtp(req);
					}else {
						resp.setResult("Select proper sigin method");
						return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
					}
				}else {
					resp.setResult("signInMethod is null Error");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setResult("User dose not exist");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignInResp resp = new SignInResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	public ResponseEntity<SignUpResp> signup(SignUpReq req){
		try {
			SignUpResp resp = new SignUpResp();
			if(userRepo.findByEmail(req.getEmail()) == null) {
				UserModel user = new UserModel();
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.SET_COOKIE, jwt.generateAccessToken(user).toString());
				headers.add(HttpHeaders.SET_COOKIE, jwt.generateRefreshToken(user).toString());
				user.setEmail(req.getEmail());
				user.setUsername(req.getUsername());
				user.setFirstName(req.getFirstName());
				user.setLastName(req.getLastName());
				user.setDoubleHashedPassword(ENutil.encrypt(req.getHashedPassword()));;
				user.setRole("user");
				userRepo.save(user);
				resp.setResult("User Signed Up Successfully");
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
			}else {
				resp.setResult("User already exist");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignUpResp resp = new SignUpResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	public ResponseEntity<RefreshResp> refresh(RefreshReq req){
		try {
			RefreshResp resp = new RefreshResp();
			if(req.getRefreshToken()!="" && req.getRefreshToken() != null) {
				if(jwt.validateRefreshToken(req.getRefreshToken())) {
					UserModel user = userRepo.findByUsername(jwt.getUsernameFromRefreshToken(req.getRefreshToken()));
					HttpHeaders headers = new HttpHeaders();
					headers.add(HttpHeaders.SET_COOKIE, jwt.generateAccessToken(user).toString());
					resp.setResult("Access token regenerated");
					return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
				}else {
					resp.setResult("Refresh token expired please login again ");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setResult("Refresh token cannot be empty ");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			RefreshResp resp = new RefreshResp();
			resp.setResult("Error: "+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
