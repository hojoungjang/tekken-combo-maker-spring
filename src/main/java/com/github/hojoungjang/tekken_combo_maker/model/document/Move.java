package com.github.hojoungjang.tekken_combo_maker.model.document;

import com.github.hojoungjang.tekken_combo_maker.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.model.enums.MoveCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document("move")
@Getter @Setter
public class Move {

    @Id
    private String id;

    private String name;
    private String command;
    private Integer damage;
    private Integer hitCount;
    private boolean counter;

    private List<Integer> startupFrames;
    private List<HitLevel> hitLevels;
    private List<Integer> hitFrames;
    private List<Integer> guardFrames;
    private Set<MoveAttribute> moveAttributes;
    private MoveCategory moveCategory;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "character_id")
//    private Character character;
    private Long characterId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "stance_id", nullable = true)
//    private Stance stance;
    private Long stanceId;
}
