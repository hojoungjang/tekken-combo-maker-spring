package com.github.hojoungjang.tekken_combo_maker.member.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequest {

    private final String email;
    private final String password;
    private final String nickname;

    public MemberCreateRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
