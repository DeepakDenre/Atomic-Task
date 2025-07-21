package com.AtomicTask.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Otp_Master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpModel {
	@Id
	private String email;
	private String otp;
	private LocalDateTime expiresAt;
}
