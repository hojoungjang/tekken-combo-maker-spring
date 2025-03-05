package com.github.hojoungjang.tekken_combo_maker.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    private String name;

    private String command;

    private int damage;

    private int hitCount;

    @Column(nullable = true)
    private Stance stance;

    /*
    private List<HitLevel> hitLevels;
    private List<int> startupFrames;
    private List<int> hitFrames;
    private List<int> guardFrames;
    private boolean counter;
    private Set<MoveAttribute> moveAttributes;
    private MoveCategory moveCategory;
    private Stance stance;
     */
}
