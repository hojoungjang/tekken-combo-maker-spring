package com.github.hojoungjang.tekken_combo_maker.move.dto;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class MoveResponse {

    private final String id;
    private final Long characterId;
    private final String stanceName;

    private final String name;
    private final String command;
    private final List<Integer> damages;
    private final Integer hitCount;
    private final boolean counter;

    private final Integer startupFrame;
    private final List<HitLevel> hitLevels;
    private final Integer hitFrame;
    private final Integer guardFrame;
    private final Set<MoveAttribute> moveAttributes;
    private final MoveCategory moveCategory;

    @Builder
    MoveResponse(
            String id,
            Long characterId,
            String stanceName,
            String name,
            String command,
            List<Integer> damages,
            Integer hitCount,
            boolean counter,
            Integer startupFrame,
            List<HitLevel> hitLevels,
            Integer hitFrame,
            Integer guardFrame,
            Set<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.id = id;
        this.characterId = characterId;
        this.stanceName = stanceName;
        this.name = name;
        this.command = command;
        this.damages = damages;
        this.hitCount = hitCount;
        this.counter = counter;
        this.startupFrame = startupFrame;
        this.hitLevels = hitLevels;
        this.hitFrame = hitFrame;
        this.guardFrame = guardFrame;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }

    public static MoveResponse fromEntity(Move move) {
        return MoveResponse.builder()
                .id(move.getId())
                .characterId(move.getCharacterId())
                .stanceName(move.getStanceName())
                .name(move.getName())
                .command(move.getCommand())
                .damages(move.getDamages())
                .hitCount(move.getHitCount())
                .counter(move.isCounter())
                .build();
    }
}
