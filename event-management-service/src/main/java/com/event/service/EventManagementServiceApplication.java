package com.event.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EventManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementServiceApplication.class, args);
	}

}
