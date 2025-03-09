package com.github.hojoungjang.tekken_combo_maker.member.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 사용자 엔티티 클래스
 */
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickName;
}
