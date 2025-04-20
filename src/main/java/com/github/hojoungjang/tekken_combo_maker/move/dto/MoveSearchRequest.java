package com.github.hojoungjang.tekken_combo_maker.move.dto;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class MoveSearchRequest {
    private final Long characterId;
    private final Integer startupFrameStart;
    private final Integer startupFrameEnd;
    private final List<MoveAttribute> moveAttributes;
    private final MoveCategory moveCategory;

    public MoveSearchRequest(
            Long characterId,
            Integer startupFrameStart,
            Integer startupFrameEnd,
            List<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.characterId = characterId;
        this.startupFrameStart = startupFrameStart;
        this.startupFrameEnd = startupFrameEnd;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }
}
