package com.github.hojoungjang.tekken_combo_maker.move.dto;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MoveResponse {

    private final String id;
    private final Long characterId;
    private final String stanceName;
    private final List<String> moveDescriptions;

    private final String name;
    private final String command;
    private final String commandDescription;
    private final List<Integer> damages;
    private final Integer hitCount;

    private final boolean counter;
    private final Integer counterFrame;

    private final Integer startupFrame;
    private final List<HitLevel> hitLevels;
    private final Integer guardFrame;

    private final Integer hitFrame;
    private final Integer hitFrameWake;
    private final Integer hitFrameEngager;
    private final HitStatus hitStatus;

    private final Set<MoveAttribute> moveAttributes;
    private final MoveCategory moveCategory;

    @Builder
    public MoveResponse(
            String id,
            Long characterId,
            String stanceName,
            List<String> moveDescriptions,
            String name,
            String command,
            String commandDescription,
            List<Integer> damages,
            Integer hitCount,
            boolean counter,
            Integer counterFrame,
            Integer startupFrame,
            List<HitLevel> hitLevels,
            Integer guardFrame,
            Integer hitFrame,
            Integer hitFrameWake,
            Integer hitFrameEngager,
            HitStatus hitStatus,
            Set<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.id = id;
        this.characterId = characterId;
        this.stanceName = stanceName;
        this.moveDescriptions = moveDescriptions;
        this.name = name;
        this.command = command;
        this.commandDescription = commandDescription;
        this.damages = damages;
        this.hitCount = hitCount;
        this.counter = counter;
        this.counterFrame = counterFrame;
        this.startupFrame = startupFrame;
        this.hitLevels = hitLevels;
        this.guardFrame = guardFrame;
        this.hitFrame = hitFrame;
        this.hitFrameWake = hitFrameWake;
        this.hitFrameEngager = hitFrameEngager;
        this.hitStatus = hitStatus;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }

    public static MoveResponse fromEntity(Move move) {

        return MoveResponse.builder()
                .id(move.getId())
                .characterId(move.getCharacterId())
                .stanceName(move.getStanceName())
                .moveDescriptions(move.getMoveDescriptions())
                .name(move.getName())
                .command(move.getCommand())
                .commandDescription(move.getCommandDescription())
                .damages(move.getDamages())
                .hitCount(move.getHitCount())
                .counter(move.isCounter())
                .counterFrame(move.getCounterFrame())
                .startupFrame(move.getStartupFrame())
                .hitLevels(move.getHitLevels())
                .guardFrame(move.getGuardFrame())
                .hitFrame(move.getHitFrame())
                .hitFrameWake(move.getHitFrameWake())
                .hitFrameEngager(move.getHitFrameEngager())
                .hitStatus(move.getHitStatus())
                .moveAttributes(move.getMoveAttributes())
                .moveCategory(move.getMoveCategory())
                .build();
    }
}
