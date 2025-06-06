package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ComboCreateRequest {

    private final String name;
    private final String description;
    private final List<String> moveIds;
    private final Integer damage;

    @Builder
    public ComboCreateRequest(
            String name,
            String description,
            List<String> moveIds,
            Integer damage
    ) {
        this.name = name;
        this.description = description;
        this.moveIds = moveIds;
        this.damage = damage;
    }
}
