package com.mindfultrader.webapp;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
	
	@Autowired
	private static DataSource datasource;

	public static void main(String[] args) {
		
		SpringApplication.run(WebApplication.class, args);
		
		
	}

}
