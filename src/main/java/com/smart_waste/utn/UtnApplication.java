package com.smart_waste.utn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "API Smart Waste UTN", version = "1.0", description = "Documentación de los endpoints del backend"))
public class UtnApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtnApplication.class, args);
	}

}
