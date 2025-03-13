package com.github.hojoungjang.tekken_combo_maker.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private record MemberResponse(Long id, String nickName) {}

    private final Long id;
    private final MemberResponse member;
    private final String title;
    private final String content;

    PostResponse(
            Long id,
            MemberResponse member,
            String title,
            String content
    ) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public static PostResponse fromEntity(Post post) {
        MemberResponse member = new MemberResponse(
                post.getMember().getId(),
                post.getMember().getNickName()
        );
        return new PostResponse(
                post.getId(),
                member,
                post.getTitle(),
                post.getContent()
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
