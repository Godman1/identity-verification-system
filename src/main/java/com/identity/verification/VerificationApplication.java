package com.identity.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VerificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerificationApplication.class, args);
	}

}
