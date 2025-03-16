package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberRepository;
import com.github.hojoungjang.tekken_combo_maker.post.controller.IPostService;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakePostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    private IPostService postService = new PostService(
            new FakePostRepository(),
            new FakeMemberRepository()
    );

    @DisplayName("게시물을 ID 를 이용해 찾을 수 있다.")
    @Test
    public void 게시물을_ID_를_이용해_찾을_수_있다() throws Exception {
        // given
        Long postId = 1L;

        // when
        PostResponse post = postService.findById(postId);

        // then
        Assertions.assertThat(post.getId()).isEqualTo(1L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 1");
        Assertions.assertThat(post.getContent()).isEqualTo("content 1");
        Assertions.assertThat(post.getMemberId()).isEqualTo(1L);
        Assertions.assertThat(post.getMemberNickName()).isEqualTo("test user 1");
    }

    @DisplayName("ID 가 매칭되는 게시물이 존재하지 않으면 찾을 수 없다.")
    @Test
    public void ID_가_매칭되지_않으면_찾을_수_없다() throws Exception {
        // given
        Long postId = 100L;

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    postService.findById(postId);
                })
                .withMessageContaining("Post not found with ID: 100");
    }

    @DisplayName("특정 멤버의 게시물을 가져올 수 있다.")
    @Test
    public void 특정_멤버의_게시물을_가져올_수_있다() throws Exception {
        // given
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<PostResponse> postPage = postService.findByMember(memberId, pageable);

        // then
        List<PostResponse> posts = postPage.getContent();
        Assertions.assertThat(posts).isNotEmpty().hasSize(1);
        Assertions.assertThat(posts.getFirst().getMemberId()).isEqualTo(1L);
        Assertions.assertThat(posts.getFirst().getId()).isEqualTo(1L);
        Assertions.assertThat(posts.getFirst().getTitle()).isEqualTo("title 1");
        Assertions.assertThat(posts.getFirst().getContent()).isEqualTo("content 1");
    }

    @DisplayName("모든 게시물을 가져올 수 있다.")
    @Test
    public void 모든_게시물을_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<PostResponse> postPage = postService.findAll(pageable);

        // then
        List<PostResponse> posts = postPage.getContent();
        Assertions.assertThat(posts).isNotEmpty().hasSize(1);
        Assertions.assertThat(posts.get(0).getMemberId()).isEqualTo(1L);
        Assertions.assertThat(posts.get(0).getId()).isEqualTo(1L);
        Assertions.assertThat(posts.get(0).getTitle()).isEqualTo("title 1");
        Assertions.assertThat(posts.get(0).getContent()).isEqualTo("content 1");
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
        Long createdPostId = postService.create(request);

        // then
        PostResponse post = postService.findById(createdPostId);
        Assertions.assertThat(post.getId()).isEqualTo(2L);
        Assertions.assertThat(post.getMemberId()).isEqualTo(2L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 2");
        Assertions.assertThat(post.getContent()).isEqualTo("content 2");
    }

    @DisplayName("주어진 멤버 ID 에 해당하는 멤버가 없으면 게시물을 만들 수 없다.")
    @Test
    public void 주어진_멤버_ID_에_해당하는_멤버가_없으면_게시물을_만들_수_없다() throws Exception {
        // given
        Long memberId = 10L;
        PostCreateRequest request = PostCreateRequest.builder()
                .memberId(memberId)
                .title("title 2")
                .content("content 2")
                .build();

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    postService.create(request);
                })
                .withMessageContaining("Member not found with ID: 10");
    }
}