package com.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	public static String SHORTENER_DOMAIN = "http://pog.ninja/";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
