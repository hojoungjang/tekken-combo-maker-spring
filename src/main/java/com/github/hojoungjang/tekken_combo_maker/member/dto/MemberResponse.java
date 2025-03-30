package com.github.hojoungjang.tekken_combo_maker.member.dto;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long id;
    private final String email;
    private final String nickname;

    @Builder
    public MemberResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
