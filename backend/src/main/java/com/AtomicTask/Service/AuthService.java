package com.AtomicTask.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.AtomicTask.DTO.SignInReq;
import com.AtomicTask.DTO.SignInResp;
import com.AtomicTask.DTO.SignUpReq;
import com.AtomicTask.DTO.SignUpResp;
import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepo;
	
	public ResponseEntity<SignInResp> signin(SignInReq req){
		try {
			SignInResp resp = new SignInResp();
			UserModel user = userRepo.findByEmail(req.getEmail());
			if(user != null) {
				if(user.getHashedPassword().equals(req.getHashedPassword())) {
					resp.setMessage("sign in successfull");
					resp.setToken("");
					return ResponseEntity.status(HttpStatus.OK).body(resp);
				}else {
					resp.setMessage("Invalid credintials");
					resp.setToken("");
					return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
				}
			}else {
				resp.setMessage("User dose not exist");
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
	
	public ResponseEntity<SignUpResp> signup(SignUpReq req){
		try {
			SignUpResp resp = new SignUpResp();
			if(userRepo.findByEmail(req.getEmail()) == null) {
				UserModel user = new UserModel();
				user.setEmail(req.getEmail());
				user.setUsername(req.getUsername());
				user.setFirstName(req.getFirstName());
				user.setLastName(req.getLastName());
				user.setHashedPassword(req.getHashedPassword());
				user.setRole("user");
				userRepo.save(user);
				resp.setMessage("User Signed Up Successfully");
				resp.setToken("");
				return ResponseEntity.status(HttpStatus.OK).body(resp);
			}else {
				resp.setMessage("User already exist");
				resp.setToken("");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
			}
		}catch (Exception e) {
			SignUpResp resp = new SignUpResp();
			resp.setMessage("Error: "+e);
			resp.setToken("");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
}
