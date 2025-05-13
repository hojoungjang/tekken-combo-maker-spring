package com.github.hojoungjang.tekken_combo_maker.character.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CharacterResponse {
    private final Long id;
    private final String name;
    private final String fullName;
    private final String label;
    private final String description;
    private final String avatarImageName;

    @Builder
    CharacterResponse(Long id, String name, String fullName, String label, String description, String avatarImageName) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.label = label;
        this.description = description;
        this.avatarImageName = avatarImageName;
    }
}
