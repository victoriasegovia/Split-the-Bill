package com.civica.splitthebill.infraestructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.civica.splitthebill")
public class SplitthebillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitthebillApplication.class, args);
	}
}
