package com.messio.lineage;

import com.messio.lineage.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ServletComponentScan
public class LineageApplication extends SpringBootServletInitializer {
	private final DataFacade facade;

	@Autowired
	public LineageApplication(DataFacade facade) {
		this.facade = facade;
		for (Company company: facade.findCompanies()){
			System.out.println(company.getName());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(LineageApplication.class, args);
	}
}
