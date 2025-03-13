package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentRepository {

    // TODO: 대댓글 조회 기능을 추가해야 한다.
    Page<Comment> findAllByPost(Long postId, Pageable pageable);
    Comment save(Comment comment);
}
