package com.github.hojoungjang.tekken_combo_maker.post.repository;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = :postId")
    Page<Comment> findAllByPost(@Param("postId") Long id, Pageable pageable);
}
