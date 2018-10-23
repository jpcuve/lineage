package com.messio.lineage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class LineageApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(LineageApplication.class, args);
	}
}
