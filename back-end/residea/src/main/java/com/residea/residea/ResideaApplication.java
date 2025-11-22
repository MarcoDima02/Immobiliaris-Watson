package com.residea.residea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ResideaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResideaApplication.class, args);
	}

}
