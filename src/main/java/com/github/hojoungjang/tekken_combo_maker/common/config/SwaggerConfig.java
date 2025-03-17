package com.github.hojoungjang.tekken_combo_maker.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "나의 스웨거 제목",
                description = "나의 스웨거 설명",
                version = "v0.0.1"),
        servers = {
                @Server(url = "http://localhost:8080/", description = "로컬 서버"),
                @Server(url = "", description = "테스트 서버"),
        }
)
@Configuration
public class SwaggerConfig {
}
