package com.github.hojoungjang.tekken_combo_maker.common.config;

import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
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

        @Bean
        OperationCustomizer commonResponseWrapper() {
                return (operation, handlerMethod) -> {

//                        final Content content = operation.getResponses().get("200").getContent();
//                        if (content != null) {
//                                content.forEach((mediaTypeKey, mediaType) -> {
//                                        Schema<?> originalSchema = mediaType.getSchema();
//                                        // Schema<?> wrappedSchema = wrapSchema(originalSchema);
//
//                                        new ObjectSchema();
//                                        Schema<Object> newSchema = new Schema<>();
//
//                                        mediaType.setSchema(wrappedSchema);
//                                });
//                        }

                        return operation;
                };
        }
}
