package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPostRepository {

    Optional<Post> findById(Long id);
    Page<Post> findByMember(Long memberId, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Post create(Post post);
}
