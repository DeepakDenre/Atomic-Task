package com.AtomicTask.Model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_Master")
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDoubleHashedPassword() {
		return doubleHashedPassword;
	}
	public void setDoubleHashedPassword(String doubleHashedPassword) {
		this.doubleHashedPassword = doubleHashedPassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UUID:"+this.uuid+"\nEmail: "+this.email+"\nUsername: "+this.username+"\nFirst Name: "+this.firstName+"\nLast Name: "+this.lastName+"\n Hashed Password: "+this.doubleHashedPassword+"\nRole :"+this.role;
	}
}
