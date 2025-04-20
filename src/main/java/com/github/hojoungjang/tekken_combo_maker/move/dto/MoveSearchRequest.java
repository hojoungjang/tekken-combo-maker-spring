package com.github.hojoungjang.tekken_combo_maker.move.dto;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class MoveSearchRequest {
    private final Long characterId;
    private final String nameSearch;
    private final Boolean counter;
    private final Integer startupFrameStart;
    private final Integer startupFrameEnd;
    private final HitLevel hitLevel;
    private final Integer guardFrameStart;
    private final Integer guardFrameEnd;
    private final List<MoveAttribute> moveAttributes;
    private final MoveCategory moveCategory;

    public MoveSearchRequest(
            Long characterId,
            String nameSearch,
            Boolean counter,
            Integer startupFrameStart,
            Integer startupFrameEnd,
            HitLevel hitLevel,
            Integer guardFrameStart,
            Integer guardFrameEnd,
            List<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.characterId = characterId;
        this.nameSearch = nameSearch;
        this.counter = counter;
        this.startupFrameStart = startupFrameStart;
        this.startupFrameEnd = startupFrameEnd;
        this.hitLevel = hitLevel;
        this.guardFrameStart = guardFrameStart;
        this.guardFrameEnd = guardFrameEnd;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }
}
