package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.post.controller.IPostService;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostService implements IPostService {

    private final IPostRepository postRepository;

    @Override
    public PostResponse findById(Long id) {
        // TODO: check for post data not found
         Post post = postRepository.findById(id).get();
         return PostResponse.fromEntity(post);
    }

    @Override
    public Page<PostResponse> findByMember(Long memberId, Pageable pageable) {
        Page<Post> memberPostPage = postRepository.findByMember(memberId, pageable);
        return memberPostPage.map(PostResponse::fromEntity);
    }

    @Override
    public Page<PostResponse> findAll(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostResponse::fromEntity);
    }

    @Override
    public Long create(PostCreateRequest request) {
        Post post = Post.builder()
                //.member()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.create(post);
        return savedPost.getId();
    }
}
