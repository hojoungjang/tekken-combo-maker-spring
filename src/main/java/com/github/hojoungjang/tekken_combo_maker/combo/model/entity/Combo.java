package com.github.hojoungjang.tekken_combo_maker.combo.model.entity;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.common.model.entity.AuditFieldEntity;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 콤보 엔티티 클래스
 * 콤보는 보통 둘 이상의 기술 (Move) 의 조합
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Combo extends AuditFieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String name;
    private int damage;
    private int hitCount;

    @Builder
    public Combo(Character character, Post post, String name, int damage, int hitCount) {
        this.character = character;
        this.post = post;
        this.name = name;
        this.damage = damage;
        this.hitCount = hitCount;
    }
}
