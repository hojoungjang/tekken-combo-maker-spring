package com.github.hojoungjang.tekken_combo_maker.post.controller;

import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentResponse;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakeCommentService;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakePostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostControllerTest {

    private PostController postController = new PostController(
            new FakePostService(),
            new FakeCommentService()
    );

    @DisplayName("ID 로 게시물 정보를 가져올 수 있다.")
    @Test
    public void ID_로_게시물_정보를_가져올_수_있다() throws Exception {
        // given
        Long postId = 1L;

        // when
        PostResponse post = postController.getById(postId);

        // then
        Assertions.assertThat(post.getId()).isEqualTo(1L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 1");
        Assertions.assertThat(post.getContent()).isEqualTo("content 1");
        Assertions.assertThat(post.getMemberId()).isEqualTo(1L);
        Assertions.assertThat(post.getMemberNickName()).isEqualTo("test user 1");
    }

    @DisplayName("여러 게시물 정보를 가져올 수 있다.")
    @Test
    public void 여러_게시물_정보를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<PostResponse> postPage = postController.getAll(pageable);

        // then
        List<PostResponse> posts = postPage.getContent();
        Assertions.assertThat(posts).isNotEmpty().hasSize(1);
        Assertions.assertThat(posts.getFirst().getMemberId()).isEqualTo(1L);
        Assertions.assertThat(posts.getFirst().getId()).isEqualTo(1L);
        Assertions.assertThat(posts.getFirst().getTitle()).isEqualTo("title 1");
        Assertions.assertThat(posts.getFirst().getContent()).isEqualTo("content 1");
    }

    @DisplayName("게시물을 만들 수 있다.")
    @Test
    public void 게시물을_만들_수_있다() throws Exception {
        // given
        Long memberId = 2L;
        PostCreateRequest request = PostCreateRequest.builder()
                .memberId(memberId)
                .title("title 2")
                .content("content 2")
                .build();

        // when
        Long postId = postController.createPost(request);

        // then
        PostResponse post = postController.getById(postId);
        Assertions.assertThat(post.getId()).isEqualTo(2L);
        Assertions.assertThat(post.getMemberId()).isEqualTo(2L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 2");
        Assertions.assertThat(post.getContent()).isEqualTo("content 2");
    }

    @DisplayName("게시물 댓글을 조회 할 수 있다.")
    @Test
    public void 게시물_댓글을_조회_할_수_있다() throws Exception {
        // given
        Long postId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CommentResponse> commentPage = postController.getAllCommentsByPost(postId, pageable);

        // then
        List<CommentResponse> comments = commentPage.getContent();
        Assertions.assertThat(comments).hasSize(3);
        Assertions.assertThat(comments)
                .extracting(CommentResponse::getContent)
                .contains("comment 1", "comment 2", "comment 3");
    }

    @DisplayName("게시물 댓글을 만들 수 있다.")
    @Test
    public void 게시물_댓글을_만들_수_있다() throws Exception {
        // given
        Long postId = 1L;
        Long memberId = 2L;
        CommentCreateRequest request = CommentCreateRequest.builder()
                .memberId(memberId)
                .content("This is a new comment by member 2")
                .build();

        // when
        Long savedCommentId = postController.createComment(postId, request);

        // then
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommentResponse> commentPage = postController.getAllCommentsByPost(postId, pageable);
        List<CommentResponse> comments = commentPage.getContent();
        Assertions.assertThat(comments)
                .filteredOn(comment -> comment.getMemberId().equals(2L))
                .singleElement()
                .extracting(CommentResponse::getContent)
                .isEqualTo("This is a new comment by member 2");
    }
}
