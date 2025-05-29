package com.AtomicTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AtomicTask.Model.OtpModel;

@Repository
public interface OtpRepository extends JpaRepository<OtpModel, String>{
	
}