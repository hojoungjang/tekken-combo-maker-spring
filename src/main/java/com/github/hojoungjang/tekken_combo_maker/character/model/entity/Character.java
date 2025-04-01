package com.github.hojoungjang.tekken_combo_maker.character.model.entity;

import com.github.hojoungjang.tekken_combo_maker.common.model.entity.AuditFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 각 캐릭터를 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String fullName;

    private String description;

    @Column(name = "avatar_image")
    private String avatarImageUrl;

    @Builder
    public Character(String name, String fullName, String description, String avatarImageUrl) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.avatarImageUrl = avatarImageUrl;
    }
}
