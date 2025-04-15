package com.github.hojoungjang.tekken_combo_maker.character.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CharacterSearchRequest {

    private final String search;

    @Builder
    public CharacterSearchRequest(String search) {
        this.search = search;
    }
}
