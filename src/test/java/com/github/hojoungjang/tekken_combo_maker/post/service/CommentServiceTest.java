package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberRepository;
import com.github.hojoungjang.tekken_combo_maker.post.controller.ICommentService;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentResponse;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakeCommentRepository;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakePostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    private final ICommentService commentService = new CommentService(
            new FakeCommentRepository(),
            new FakePostRepository(),
            new FakeMemberRepository()
    );

    @DisplayName("게시물의 댓글을 조회 할 수 있다.")
    @Test
    public void 게시물의_댓글을_조회_할_수_있다() throws Exception {
        // given
        Long postId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CommentResponse> commentPage = commentService.findAllByPost(postId, pageable);

        // then
        List<CommentResponse> comments = commentPage.getContent();
        Assertions.assertThat(comments).isNotEmpty().hasSize(3);
        Assertions.assertThat(comments)
                .extracting(CommentResponse::getContent)
                .contains("comment 1", "comment 2", "comment 3");
    }

    @DisplayName("게시물을 만들 수 있다.")
    @Test
    public void 게시물을_만들_수_있다() throws Exception {
        // given
        Long postId = 1L;
        CommentCreateRequest request = CommentCreateRequest.builder()
                .memberId(2L)
                .content("This is a new comment")
                .build();

        // when
        Long savedCommentId = commentService.create(postId, request);

        // then
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommentResponse> commentPage = commentService.findAllByPost(1L, pageable);
        List<CommentResponse> comments = commentPage.getContent();
        Assertions.assertThat(comments).isNotEmpty().hasSize(4);
        Assertions.assertThat(comments)
                .extracting(CommentResponse::getContent)
                .contains("This is a new comment");
    }
}
