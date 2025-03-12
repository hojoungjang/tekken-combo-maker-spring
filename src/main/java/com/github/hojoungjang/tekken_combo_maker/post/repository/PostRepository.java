package com.github.hojoungjang.tekken_combo_maker.post.repository;

import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import com.github.hojoungjang.tekken_combo_maker.post.service.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class PostRepository implements IPostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public Page<Post> findByMember(Long memberId, Pageable pageable) {
        return postJpaRepository.findAllByMember(memberId, pageable);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Post create(Post post) {
        return postJpaRepository.save(post);
    }
}
