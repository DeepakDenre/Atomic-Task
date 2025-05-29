package com.AtomicTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.AtomicTask.Config.EnvConfig;

@SpringBootApplication
@EnableConfigurationProperties(EnvConfig.class)
public class AtomicTask {

	public static void main(String[] args) {
		SpringApplication.run(AtomicTask.class, args);
	}

}
