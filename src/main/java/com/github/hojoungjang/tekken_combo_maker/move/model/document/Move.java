package com.github.hojoungjang.tekken_combo_maker.move.model.document;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Builder;
import lombok.Getter;
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
@Document("move")
@Getter
public class Move {

    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private Long characterId;       // character 테이블 primary key
    private String stanceName;      // stance 테이블 primary key

    private String name;
    private String command;
    private List<Integer> damages;
    private Integer hitCount;
    private boolean counter;

    private Integer startupFrame;               // 발동 프레임
    private List<HitLevel> hitLevels;           // 타수별 타점 판정
    private Integer hitFrame;                   // 적중시 프레임 격차
    private Integer guardFrame;                 // 가드시 프레임 격차
    private Set<MoveAttribute> moveAttributes;  // 기술 특수 효과
    private MoveCategory moveCategory;          // 기술 분류

    @Builder
    Move(
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
}
