package com.ademozalp.yemek_burada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YemekBuradaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YemekBuradaApplication.class, args);
	}

}
