package com.github.hojoungjang.tekken_combo_maker.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "users")
@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickName;
}
