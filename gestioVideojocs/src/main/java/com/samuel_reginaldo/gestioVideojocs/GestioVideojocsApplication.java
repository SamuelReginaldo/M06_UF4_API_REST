package com.samuel_reginaldo.gestioVideojocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.samuel_reginaldo.gestioVideojocs.backend.integration.repositories")
@EntityScan("com.samuel_reginaldo.gestioVideojocs.backend.business.model")
public class GestioVideojocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioVideojocsApplication.class, args);
	}

}
