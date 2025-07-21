package com.AtomicTask.Model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "User_Master")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	@Column(name = "Email",nullable = false,unique = true)
	private String email;
	
	@Column(name = "DoubleHashedPassword",nullable = false,unique = true)
	private String doubleHashedPassword;
	
	@Column(name = "Username",nullable = false,unique = true)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	@Column(name = "Role",nullable = false,unique = true)
	private String role;
	
	@OneToMany
	private List<TaskPageModel> taskPages;
}
