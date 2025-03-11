package com.github.hojoungjang.tekken_combo_maker.post.model.entity;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시물 댓글 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 답변 댓글 여부 (대댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Comment thread;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Member member, Post post, Comment thread, String content) {
        this.member = member;
        this.post = post;
        this.thread = thread;
        this.content = content;
    }
}
