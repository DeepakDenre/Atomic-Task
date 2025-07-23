package com.AtomicTask.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.AtomicTask.DTO.OTP.OtpReq;
import com.AtomicTask.DTO.OTP.OtpResp;
import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	OtpService otp;
	
	@Autowired
	JavaMailSender mailSender;
	
	public ResponseEntity<OtpResp> sendOtp(OtpReq req) {
		try {
			UserModel user = userRepo.findByEmail(req.getEmail());
			OtpResp resp = new OtpResp();
			if(user != null) {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				String subject = "🔐 Your Atomic Task OTP Code";
				String htmlContent = generateHtml(user.getUsername(), otp.generateOtp(req));
				helper.setTo(req.getEmail());
				helper.setSubject(subject);
				helper.setText(htmlContent, true);
				mailSender.send(message);
				resp.setMessage("Otp Sent to your Email");
				return ResponseEntity.status(HttpStatus.OK).body(resp);
			}else {
				resp.setMessage("email not registered");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
			}
		}catch (Exception e) {
			OtpResp resp = new OtpResp();
			resp.setMessage("Otp generation failed");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}
	
	private String generateHtml(String userName, String otpCode) {
	    return """
	        <!DOCTYPE html>
	        <html>
	        <head>
	          <style>
	            .container {
	              background-color: #f4f4f4;
	              padding: 30px;
	              font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	              color: #333;
	            }
	            .card {
	              background-color: #ffffff;
	              border-radius: 12px;
	              padding: 25px;
	              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	              max-width: 500px;
	              margin: auto;
	            }
	            .header {
	              text-align: center;
	              color: #007bff;
	              font-size: 28px;
	              font-weight: bold;
	              margin-bottom: 10px;
	            }
	            .otp-section {
	              text-align: center;
	              margin: 20px 0;
	            }
	            .otp-code {
	              display: inline-block;
	              font-size: 32px;
	              font-weight: bold;
	              color: #ff5722;
	              letter-spacing: 5px;
	              padding: 12px 18px;
	              background-color: #f0f0f0;
	              border-radius: 8px;
	              user-select: all;
	            }
	            .copy-hint {
	              font-size: 12px;
	              color: #777;
	              margin-top: 8px;
	            }
	            .footer {
	              font-size: 14px;
	              color: #888;
	              text-align: center;
	              margin-top: 20px;
	            }
	          </style>
	        </head>
	        <body>
	          <div class="container">
	            <div class="card">
	              <div class="header">🔐 Atomic Task OTP Verification</div>
	              <p>Hello <strong>%s</strong>,</p>
	              <p>To complete your sign-in, use the OTP below. This code is valid for <strong>10 minutes</strong>.</p>
	              <div class="otp-section">
	                <div class="otp-code">%s</div>
	                <div class="copy-hint">📋 You can select and copy this code manually.</div>
	              </div>
	              <p>If you did not request this OTP, please ignore this email.</p>
	              <div class="footer">Atomic Task • Simplify Your Goals, One Task at a Time</div>
	            </div>
	          </div>
	        </body>
	        </html>
	        """.formatted(userName, otpCode);
	}

}
