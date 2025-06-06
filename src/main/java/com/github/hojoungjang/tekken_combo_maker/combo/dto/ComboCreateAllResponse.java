package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ComboCreateAllResponse {

    private final List<Long> comboIds;

    @Builder
    public ComboCreateAllResponse(List<Long> comboIds) {
        this.comboIds = comboIds;
    }
}
