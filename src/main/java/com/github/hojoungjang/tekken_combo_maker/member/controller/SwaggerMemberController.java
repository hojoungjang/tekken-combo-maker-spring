package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseErrorResponse;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Member", description = "Member (유저) API")
public interface SwaggerMemberController {

    @Operation(summary = "ID 로 멤버 조회", description = "ID 를 이용해 멤버를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID 로 멤버를 성공적으로 조회"),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID 에 대한 멤버가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))
            )
    })
    MemberResponse getById(
            @Parameter(description = "멤버 ID", example = "1") Long id
    );

    @Operation(summary = "멤버 목록 조회", description = "전체 멤버를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버를 성공적으로 조회")
    })
    Page<MemberResponse> getAll(
            @ParameterObject Pageable pageable
    );
}
