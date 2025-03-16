package com.github.hojoungjang.tekken_combo_maker.post.service;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.service.IMemberRepository;
import com.github.hojoungjang.tekken_combo_maker.post.controller.IPostService;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final IPostRepository postRepository;
    private final IMemberRepository memberRepository;

    @Override
    public PostResponse findById(Long id) {
         Post post = postRepository.findById(id)
                 .orElseThrow(() -> NotFoundException.supplier(String.format("Post not found with ID: %d", id)));
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
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> NotFoundException.supplier(
                        String.format("Member not found with ID: %d", request.getMemberId())
                ));
        Post post = Post.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.create(post);
        return savedPost.getId();
    }
}
