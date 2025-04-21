package com.github.hojoungjang.tekken_combo_maker.move.controller;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Move", description = "캐릭터 기술")
public interface SwaggerMoveController {

    @Operation(summary = "캐릭터 기술 검색", description = "검색 조건을 통해 캐릭터을 기술을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 기술 성공적으로 검색 및 조회")
    })
    Page<MoveResponse> searchAll(
            @ParameterObject MoveSearchRequest request,
            @ParameterObject Pageable pageable
    );
}
