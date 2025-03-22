package com.github.hojoungjang.tekken_combo_maker.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    //	@Bean
    //	public AuditorAware<String> auditorProvider() {
    //		return () -> Optional.of(UUID.randomUUID().toString());
    //	}
}
