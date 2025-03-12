package com.github.hojoungjang.tekken_combo_maker.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    private final Long memberId;
    private final String title;
    private final String content;

    // TODO: need more thinking on how to add combo to post
    // private final List<Long> comboIds;

    @Builder
    public PostCreateRequest(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
