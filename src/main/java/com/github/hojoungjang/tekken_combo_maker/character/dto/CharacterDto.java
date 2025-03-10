package com.github.hojoungjang.tekken_combo_maker.character.dto;

import lombok.Getter;
import lombok.Setter;

// TODO: use builder or better ways to map between entity and DTO
@Getter @Setter
public class CharacterDto {
    private Long id;
    private String name;
    private String description;
    private String avatarImageUrl;
}
