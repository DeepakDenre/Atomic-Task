package com.AtomicTask.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.AtomicTask.DTO.RefreshReq;
import com.AtomicTask.DTO.RefreshResp;
import com.AtomicTask.DTO.SignInReq;
import com.AtomicTask.DTO.SignInResp;
import com.AtomicTask.DTO.SignUpReq;
import com.AtomicTask.DTO.SignUpResp;
import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.UserRepository;
import com.AtomicTask.Security.EncryptionUtils;
import com.AtomicTask.Security.JWTUtils;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JWTUtils jwt;
	
	@Autowired
	OtpService otp;
	
	public ResponseEntity<SignInResp> signin(SignInReq req){
		try {
			SignInResp resp = new SignInResp();
			UserModel user = userRepo.findByEmail(req.getEmail());
			if(user != null) {
				if(req.getSignInMethod() != null) {
					if(req.getSignInMethod().equals("password")) {
						if(EncryptionUtils.decrypt(user.getDoubleHashedPassword()).equals(req.getHashedPassword())) {
							resp.setMessage("password sign in successfull");
							resp.setAccessToken(jwt.generateAccessToken(user));
							resp.setRefreshToken(jwt.generateRefreshToken(user));
							return ResponseEntity.status(HttpStatus.OK).body(resp);
						}else {
							resp.setMessage("Invalid credintials");
							resp.setAccessToken("");
							resp.setRefreshToken("");
							return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
						}
					}else if(req.getSignInMethod().equals("otp")) {
						return otp.validateOtp(req);
					}else {
						resp.setMessage("Select proper sigin method");
						resp.setAccessToken("");
						resp.setRefreshToken("");
						return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
					}
				}else {
					resp.setMessage("signInMethod is null Error");
					resp.setAccessToken("");
					resp.setRefreshToken("");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setMessage("User dose not exist");
				resp.setAccessToken("");
				resp.setRefreshToken("");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignInResp resp = new SignInResp();
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			resp.setRefreshToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	public ResponseEntity<SignUpResp> signup(SignUpReq req){
		try {
			SignUpResp resp = new SignUpResp();
			if(userRepo.findByEmail(req.getEmail()) == null) {
				UserModel user = new UserModel();
				user.setEmail(req.getEmail());
				user.setUsername(req.getUsername());
				user.setFirstName(req.getFirstName());
				user.setLastName(req.getLastName());
				user.setDoubleHashedPassword(EncryptionUtils.encrypt(req.getHashedPassword()));;
				user.setRole("user");
				userRepo.save(user);
				resp.setMessage("User Signed Up Successfully");
				resp.setAccessToken(jwt.generateAccessToken(user));
				resp.setRefreshToken(jwt.generateRefreshToken(user));
				return ResponseEntity.status(HttpStatus.OK).body(resp);
			}else {
				resp.setMessage("User already exist");
				resp.setAccessToken("");
				resp.setRefreshToken("");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignUpResp resp = new SignUpResp();
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			resp.setRefreshToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	public ResponseEntity<RefreshResp> refresh(RefreshReq req){
		try {
			RefreshResp resp = new RefreshResp();
			if(req.getRefreshToken()!="" && req.getRefreshToken() != null) {
				if(jwt.validateRefreshToken(req.getRefreshToken())) {
					UserModel user = userRepo.findByUsername(jwt.getUsernameFromRefreshToken(req.getRefreshToken()));
					resp.setMessage("Access token regenerated");
					resp.setAccessToken(jwt.generateAccessToken(user));
					return ResponseEntity.status(HttpStatus.OK).body(resp);
				}else {
					resp.setMessage("Refresh token expired please login again ");
					resp.setAccessToken("");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setMessage("Refresh token cannot be empty ");
				resp.setAccessToken("");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			RefreshResp resp = new RefreshResp();
			resp.setMessage("Error: "+e);
			resp.setAccessToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
