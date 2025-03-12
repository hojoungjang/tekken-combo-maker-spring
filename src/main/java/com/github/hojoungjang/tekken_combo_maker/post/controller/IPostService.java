package com.github.hojoungjang.tekken_combo_maker.post.controller;

import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {

    PostResponse findById(Long id);
    Page<PostResponse> findByMember(Long memberId, Pageable pageable);
    Page<PostResponse> findAll(Pageable pageable);
    Long create(PostCreateRequest request);
}
