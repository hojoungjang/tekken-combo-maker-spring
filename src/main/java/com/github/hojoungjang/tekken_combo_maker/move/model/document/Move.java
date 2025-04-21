package com.github.hojoungjang.tekken_combo_maker.move.model.document;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * 캐릭터의 기술을 나타내는 도큐먼트 클래스
 * 기술의 메타데이터를 담고 있습니다.
 */
@QueryEntity
@Document("move")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Move {

    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private Long characterId;       // character 테이블 primary key
    private String stanceName;      // stance 테이블의 스탠스 이름 (TODO: 스탠스이름과 캐릭터 ID 를 조합해 찾아야될수도 있음)

    private String name;
    private String command;
    private String commandDescription;
    private List<Integer> damages;
    private Integer hitCount;
    private boolean counter;

    private Integer startupFrame;               // 발동 프레임
    private List<HitLevel> hitLevels;           // 타수별 타점 판정
    private Integer guardFrame;                 // 가드시 프레임 격차

    private Integer hitFrame;                   // 적중시 프레임 격차
    private Integer hitFrameWake;               // 상대가 낙법한 경우 프레임 격차
    private Integer hitFrameEngager;            // 인게이져 발동시 프레임 격차
    private HitStatus hitStatus;                // 기술 적중후 상대 상태

    private CleanHitInfo cleanHitInfo;          // 클린 히트 일때 프레임 정보

    private Set<MoveAttribute> moveAttributes;  // 기술 특수 효과
    private MoveCategory moveCategory;          // 기술 분류

    @Builder
    Move(
            Long characterId,
            String stanceName,
            String name,
            String command,
            String commandDescription,
            List<Integer> damages,
            Integer hitCount,
            boolean counter,
            Integer startupFrame,
            List<HitLevel> hitLevels,
            Integer guardFrame,
            Integer hitFrame,
            Integer hitFrameWake,
            Integer hitFrameEngager,
            HitStatus hitStatus,
            CleanHitInfo cleanHitInfo,
            Set<MoveAttribute> moveAttributes,
            MoveCategory moveCategory
    ) {
        this.characterId = characterId;
        this.stanceName = stanceName;
        this.name = name;
        this.command = command;
        this.commandDescription = commandDescription;
        this.damages = damages;
        this.hitCount = hitCount;
        this.counter = counter;
        this.startupFrame = startupFrame;
        this.hitLevels = hitLevels;
        this.guardFrame = guardFrame;
        this.hitFrame = hitFrame;
        this.hitFrameWake = hitFrameWake;
        this.hitFrameEngager = hitFrameEngager;
        this.hitStatus = hitStatus;
        this.cleanHitInfo = cleanHitInfo;
        this.moveAttributes = moveAttributes;
        this.moveCategory = moveCategory;
    }
}
