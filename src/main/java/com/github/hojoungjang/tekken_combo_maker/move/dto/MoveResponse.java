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
    private final Long stanceId;

    private final String name;
    private final String command;
    private final Integer damage;
    private final Integer hitCount;
    private final boolean counter;

    private final List<Integer> startupFrames;
    private final List<HitLevel> hitLevels;
    private final List<Integer> hitFrames;
    private final List<Integer> guardFrames;
    private final Set<MoveAttribute> moveAttributes;
    private final MoveCategory moveCategory;

    @Builder
    MoveResponse(
            String id,
            Long characterId,
            Long stanceId,
            String name,
            String command,
            Integer damage,
            Integer hitCount,
            boolean counter,
            List<Integer> startupFrames,
            List<HitLevel> hitLevels,
            List<Integer> hitFrames,
            List<Integer> guardFrames,
            Set<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.id = id;
        this.characterId = characterId;
        this.stanceId = stanceId;
        this.name = name;
        this.command = command;
        this.damage = damage;
        this.hitCount = hitCount;
        this.counter = counter;
        this.startupFrames = startupFrames;
        this.hitLevels = hitLevels;
        this.hitFrames = hitFrames;
        this.guardFrames = guardFrames;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }

    public static MoveResponse fromEntity(Move move) {
        return MoveResponse.builder()
                .id(move.getId())
                .characterId(move.getCharacterId())
                .stanceId(move.getStanceId())
                .name(move.getName())
                .command(move.getCommand())
                .damage(move.getDamage())
                .hitCount(move.getHitCount())
                .counter(move.isCounter())
                .build();
    }
}
