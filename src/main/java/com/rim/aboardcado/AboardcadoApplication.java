package com.rim.aboardcado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AboardcadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AboardcadoApplication.class, args);
	}

}
