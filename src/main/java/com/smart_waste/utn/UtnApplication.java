package com.smart_waste.utn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UtnApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtnApplication.class, args);
	}

}
