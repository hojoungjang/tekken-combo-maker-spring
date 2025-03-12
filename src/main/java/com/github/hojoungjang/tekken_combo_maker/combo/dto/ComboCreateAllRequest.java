package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ComboCreateAllRequest {

    private List<ComboCreateRequest> combos = new ArrayList<>();

    @Builder
    public ComboCreateAllRequest(List<ComboCreateRequest> combos) {
        this.combos = combos;
    }
}
