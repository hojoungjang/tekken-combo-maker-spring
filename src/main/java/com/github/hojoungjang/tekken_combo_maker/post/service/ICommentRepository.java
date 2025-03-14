package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICommentRepository {

    // TODO: 대댓글 조회 기능을 추가해야 한다.
    Page<Comment> findAllByPost(Long postId, Pageable pageable);
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);
}
