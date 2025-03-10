package com.github.hojoungjang.tekken_combo_maker.move.model.entity;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 캐릭터별 자세 엔티티 클래스
 */
@Entity
@Getter
public class Stance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    private String name;
}
