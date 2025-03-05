package com.github.hojoungjang.tekken_combo_maker.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "thread")
@Entity
@Getter
public class CommentThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;
}
