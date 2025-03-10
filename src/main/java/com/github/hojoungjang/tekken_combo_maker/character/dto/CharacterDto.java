package com.github.hojoungjang.tekken_combo_maker.character.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CharacterDto {
    private final Long id;
    private final String name;
    private final String description;
    private final String avatarImageUrl;

    @Builder
    CharacterDto(Long id, String name, String description, String avatarImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatarImageUrl = avatarImageUrl;
    }
}
