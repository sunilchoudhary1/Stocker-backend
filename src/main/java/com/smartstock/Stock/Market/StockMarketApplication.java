package com.smartstock.Stock.Market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = "com.smartstock.Stock.Market.config")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StockMarketApplication { // user

	public static void main(String[] args) {
		SpringApplication.run(StockMarketApplication.class, args);
	}

}
