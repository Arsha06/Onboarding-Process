package com.arshacodes.onboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnboardApplication.class, args);
		System.out.println("Congrats Arsha! The API is running fine");
	}
}

