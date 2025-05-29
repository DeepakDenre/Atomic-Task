package com.AtomicTask.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AtomicTask.Model.UserModel;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>{
	public UserModel findByEmail(String email);
	public UserModel findByUsername(String username);
}