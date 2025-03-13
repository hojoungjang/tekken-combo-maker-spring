package com.github.hojoungjang.tekken_combo_maker.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    private final Long memberId;
    private final Long threadId;
    private final String content;

    @Builder
    CommentCreateRequest(
            Long memberId,
            Long threadId,
            String content
    ) {
        this.memberId = memberId;
        this.threadId = threadId;
        this.content = content;
    }
}
