package com.github.hojoungjang.tekken_combo_maker.common.dto.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public interface SwaggerBaseResponse {


    boolean success = false;

    @Schema(description = "응답 데이터")
    Object data = null;
}
