package com.github.hojoungjang.tekken_combo_maker.post.repository;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import com.github.hojoungjang.tekken_combo_maker.post.service.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository implements ICommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Page<Comment> findAllByPost(Long postId, Pageable pageable) {
        return commentJpaRepository.findAllByPost(postId, pageable);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }
}
