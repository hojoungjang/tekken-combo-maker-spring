package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ComboCreateRequest {

    // TODO: CharacterId might be unnecessary; Confirm the schema
    private Long characterId;
    private String name;
    private Integer damage;
    private Integer hitCount;

    @Builder
    public ComboCreateRequest(Long characterId, String name, Integer damage, Integer hitCount) {
        this.characterId = characterId;
        this.name = name;
        this.damage = damage;
        this.hitCount = hitCount;
    }
}
