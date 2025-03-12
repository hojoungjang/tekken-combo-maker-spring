package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.controller.IPostService;
import com.github.hojoungjang.tekken_combo_maker.post.mock.FakePostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    private IPostService postService = new PostService(new FakePostRepository());

    @DisplayName("게시물을 ID 를 이용해 찾을 수 있다.")
    @Test
    public void 게시물을_ID_를_이용해_찾을_수_있다() throws Exception {

    }

    @DisplayName("특정 멤버의 게시물을 가져올 수 있다.")
    @Test
    public void 특정_멤버의_게시물을_가져올_수_있다() throws Exception {

    }

    @DisplayName("모든 게시물을 가져올 수 있다.")
    @Test
    public void 모든_게시물을_가져올_수_있다() throws Exception {

    }

    @DisplayName("게시물을 만들 수 있다.")
    @Test
    public void 게시물을_만들_수_있다() throws Exception {

    }
}