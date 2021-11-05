package com.portfolio.complete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CompleteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompleteApplication.class, args);
	}

}
