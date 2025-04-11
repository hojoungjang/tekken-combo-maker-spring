package com.github.hojoungjang.tekken_combo_maker.move.model.document;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CleanHitInfo {
    private List<Integer> damages;
    private Integer hitFrame;
    private Integer hitFrameWake;
    private HitStatus hitStatus;

    @Builder
    public CleanHitInfo(List<Integer> damages, Integer hitFrame, Integer hitFrameWake, HitStatus hitStatus) {
        this.damages = damages;
        this.hitFrame = hitFrame;
        this.hitFrameWake = hitFrameWake;
        this.hitStatus = hitStatus;
    }
}
