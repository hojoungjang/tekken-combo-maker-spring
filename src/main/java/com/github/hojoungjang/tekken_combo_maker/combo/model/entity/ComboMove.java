package com.github.hojoungjang.tekken_combo_maker.combo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Combo 와 Move 의 Many-to-Many 관계를 연결하는 중간 테이블용 엔티티
 */
@Entity
@Getter
public class ComboMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_id", nullable = false)
    private Combo combo;

    @Column(nullable = false)
    private String moveId;      // MongoDB 도큐먼트 ID
}
