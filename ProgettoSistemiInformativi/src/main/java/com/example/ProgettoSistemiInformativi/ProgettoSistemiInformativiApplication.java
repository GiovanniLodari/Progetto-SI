package com.example.ProgettoSistemiInformativi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class ProgettoSistemiInformativiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProgettoSistemiInformativiApplication.class, args);
	}
}
