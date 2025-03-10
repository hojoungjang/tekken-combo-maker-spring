package com.github.hojoungjang.tekken_combo_maker.post.model.entity;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 게시물 엔티티 클래스
 */
@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // private long likes;
    // private long dislikes;
}
