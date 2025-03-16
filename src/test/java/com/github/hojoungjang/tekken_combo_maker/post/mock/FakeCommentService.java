package com.github.hojoungjang.tekken_combo_maker.post.mock;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberRepository;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.service.IMemberRepository;
import com.github.hojoungjang.tekken_combo_maker.post.controller.ICommentService;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentResponse;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import com.github.hojoungjang.tekken_combo_maker.post.service.ICommentRepository;
import com.github.hojoungjang.tekken_combo_maker.post.service.IPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakeCommentService implements ICommentService {

    private final ICommentRepository commentRepository = new FakeCommentRepository();
    private final IPostRepository postRepository = new FakePostRepository();
    private final IMemberRepository memberRepository = new FakeMemberRepository();

    @Override
    public Page<CommentResponse> findAllByPost(Long postId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findAllByPost(postId, pageable);
        return commentPage.map(CommentResponse::fromEntity);
    }

    @Override
    public Long create(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> NotFoundException.supplier(String.format("Post not found with ID: %d", postId)));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> NotFoundException.supplier(
                        String.format("Member not found with ID: %d", request.getMemberId())
                ));

        Comment thread = null;
        if (request.getThreadId() != null) {
            thread = commentRepository.findById(request.getThreadId())
                    .orElseThrow(() -> NotFoundException.supplier(
                            String.format("Comment not found with ID: %d", request.getThreadId())
                    ));
        }
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .thread(thread)
                .content(request.getContent())
                .build();
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }
}
