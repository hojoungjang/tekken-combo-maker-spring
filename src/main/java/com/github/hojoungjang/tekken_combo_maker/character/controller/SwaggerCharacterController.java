package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseErrorResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "Character", description = "캐릭터 API")
public interface SwaggerCharacterController {

    @Operation(summary = "ID 로 캐릭터 조회", description = "ID 를 이용해 캐릭터를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID 로 캐릭터를 성공적으로 조회"),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID 에 대한 캐릭터가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))
            )
    })
    CharacterDto getById(@Parameter(description = "캐릭터 ID", example = "1") Long id);

    @Operation(summary = "캐릭터 목록 조회", description = "전체 캐릭터를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터를 성공적으로 조회")
    })
    Page<CharacterDto> getAll(@ParameterObject Pageable pageable);

    @Operation(summary = "캐릭터 콤보 목록 조회", description = "캐릭터의 전체 콤보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 콤보 성공적으로 조회")
    })
    Page<ComboDto> getAllCombos(
            @Parameter(description = "캐릭터 ID", example = "1") Long id,
            @ParameterObject Pageable pageable
    );

    @Operation(summary = "캐릭터 콤보 생성", description = "캐릭터 콤보를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 콤보 성공적으로 생성")
    })
    List<Long> createAllCombo(@RequestBody(description = "콤보 데이터") ComboCreateAllRequest request);

    @Operation(summary = "캐릭터 기술 목록 조회", description = "캐릭터의 전체 기술을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 기술 성공적으로 조회")
    })
    Page<MoveResponse> getAllMoves(
            @Parameter(description = "캐릭터 ID", example = "1") Long id,
            @ParameterObject Pageable pageable
    );
}
