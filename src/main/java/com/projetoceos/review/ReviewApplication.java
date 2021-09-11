package com.projetoceos.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@EnableFeignClients
public class ReviewApplication {

	private static final Logger LOGGER=LoggerFactory.getLogger(ReviewApplication.class);
 
	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);

		LOGGER.info("INICIOU!");
	}

}