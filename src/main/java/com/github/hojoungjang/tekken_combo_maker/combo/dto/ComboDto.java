package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import lombok.Getter;

@Getter
public class ComboDto {

    record ComboCharacterDto(Long id, String name) {
    }

    record ComboPostDto(Long id, Long authorId) {
    }

    private final Long id;
    private final ComboCharacterDto character;
    private final ComboPostDto post;
    private final String name;
    private final int damage;
    private final int hitCount;

    ComboDto(
            Long id,
            ComboCharacterDto character,
            ComboPostDto post,
            String name,
            int damage,
            int hitCount
    ) {
        this.id = id;
        this.character = character;
        this.post = post;
        this.name = name;
        this.damage = damage;
        this.hitCount = hitCount;
    }

    public static ComboDto fromEntity(Combo combo) {
        ComboCharacterDto character = new ComboCharacterDto(
                combo.getCharacter().getId(),
                combo.getCharacter().getName()
        );

        // TODO: post is optional; How do I effectively check for undefined post value?
        ComboPostDto post = null;
        if (combo.getPost() != null) {
            post = new ComboPostDto(
                    combo.getPost().getId(),
                    combo.getPost().getMember().getId()
            );
        }

        return new ComboDto(
                combo.getId(),
                character,
                post,
                combo.getName(),
                combo.getDamage(),
                combo.getHitCount()
        );
    }
}
