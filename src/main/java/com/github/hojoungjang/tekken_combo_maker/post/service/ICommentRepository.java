package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentRepository {

    Page<Comment> findAllByPost(Long postId, Pageable pageable);
}
