package com.github.hojoungjang.tekken_combo_maker.post.repository;

import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.member.id = :memberId")
    Page<Post> findAllByMember(@Param("memberId") Long memberId, Pageable pageable);
}
