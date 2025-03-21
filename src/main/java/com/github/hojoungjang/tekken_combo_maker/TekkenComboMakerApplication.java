package com.github.hojoungjang.tekken_combo_maker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class TekkenComboMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TekkenComboMakerApplication.class, args);
	}

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return () -> Optional.of(UUID.randomUUID().toString());
//	}
}
