package com.github.hojoungjang.tekken_combo_maker.combo.model.entity;

import com.github.hojoungjang.tekken_combo_maker.common.model.entity.AuditFieldEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Combo 와 Move 의 Many-to-Many 관계를 연결하는 중간 테이블용 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComboMove extends AuditFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_id", nullable = false)
    private Combo combo;

    @Column(nullable = false)
    private String moveId;      // MongoDB 도큐먼트 ID

    @Builder
    public ComboMove(Combo combo, String moveId) {
        this.combo = combo;
        this.moveId = moveId;
    }
}
