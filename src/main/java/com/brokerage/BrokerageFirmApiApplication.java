package com.brokerage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BrokerageFirmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerageFirmApiApplication.class, args);
	}

}
