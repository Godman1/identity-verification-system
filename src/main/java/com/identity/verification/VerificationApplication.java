package com.identity.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebMvc
public class VerificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerificationApplication.class, args);
	}

}
