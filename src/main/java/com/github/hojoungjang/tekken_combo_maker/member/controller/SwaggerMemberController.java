package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member", description = "Member (유저) API")
public interface SwaggerMemberController {

    @Operation(summary = "멤버 조회", description = "ID 를 이용해 멤버를 조회합니다.")
    MemberResponse getById(Long id);
}
