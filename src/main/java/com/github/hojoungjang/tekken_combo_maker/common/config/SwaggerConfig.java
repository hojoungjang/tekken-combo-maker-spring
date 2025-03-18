package com.github.hojoungjang.tekken_combo_maker.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Tekken Combo Maker API",
                description = "철권 콤보 메이커 API 목록",
                version = "v0.1.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080/", description = "로컬 서버"),
                @Server(url = "", description = "테스트 서버"),
        }
)
@Configuration
public class SwaggerConfig {
}
