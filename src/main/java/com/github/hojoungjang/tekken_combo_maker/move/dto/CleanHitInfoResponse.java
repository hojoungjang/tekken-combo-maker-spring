package com.github.hojoungjang.tekken_combo_maker.move.dto;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.CleanHitInfo;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CleanHitInfoResponse {

    private final List<Integer> damages;
    private final Integer hitFrame;
    private final Integer hitFrameWake;
    private final HitStatus hitStatus;

    @Builder
    public CleanHitInfoResponse(
            List<Integer> damages,
            Integer hitFrame,
            Integer hitFrameWake,
            HitStatus hitStatus
    ) {
        this.damages = damages;
        this.hitFrame = hitFrame;
        this.hitFrameWake = hitFrameWake;
        this.hitStatus = hitStatus;
    }

    public static CleanHitInfoResponse fromEntity(CleanHitInfo cleanHitInfo) {
        return CleanHitInfoResponse.builder()
                .damages(cleanHitInfo.getDamages())
                .hitFrame(cleanHitInfo.getHitFrame())
                .hitFrameWake(cleanHitInfo.getHitFrameWake())
                .hitStatus(cleanHitInfo.getHitStatus())
                .build();
    }
}
