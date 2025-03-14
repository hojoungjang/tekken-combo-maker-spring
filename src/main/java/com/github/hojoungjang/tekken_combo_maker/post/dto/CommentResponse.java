package com.github.hojoungjang.tekken_combo_maker.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {

    private record MemberResponse(Long id, String nickName) {}

    private final Long id;
    private final MemberResponse member;
    private final Long postId;
    private final String content;

    CommentResponse(
            Long id,
            MemberResponse member,
            Long postId,
            String content
    ) {
        this.id = id;
        this.member = member;
        this.postId = postId;
        this.content = content;

        // TODO: add 대댓글 count
    }

    public static CommentResponse fromEntity(Comment comment) {
            MemberResponse member = new MemberResponse(
                    comment.getMember().getId(),
                    comment.getMember().getNickName()
            );
            return new CommentResponse(
                    comment.getId(),
                    member,
                    comment.getPost().getId(),
                    comment.getContent()
            );
    }

    @JsonIgnore
    public Long getMemberId() {
        return member.id();
    }

    @JsonIgnore
    public String getMemberNickName() {
        return member.nickName();
    }
}
