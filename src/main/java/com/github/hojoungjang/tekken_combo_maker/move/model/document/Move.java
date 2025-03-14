package com.github.hojoungjang.tekken_combo_maker.move.model.document;

import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private Long characterId;   // character 테이블 primary key
    private Long stanceId;      // stance 테이블 primary key

    private String name;
    private String command;
    private Integer damage;
    private Integer hitCount;
    private boolean counter;

    private List<Integer> startupFrames;        // 타수별 발동 프레임
    private List<HitLevel> hitLevels;           // 타수별 타점 판정
    private List<Integer> hitFrames;            // 타수별 적중시 프레임 격차
    private List<Integer> guardFrames;          // 타수별 가드시 프레임 격차
    private Set<MoveAttribute> moveAttributes;  // 기술 특수 효과
    private MoveCategory moveCategory;          // 기술 분류
}
