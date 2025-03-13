package com.github.hojoungjang.tekken_combo_maker.post.mock;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Comment;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import com.github.hojoungjang.tekken_combo_maker.post.service.ICommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeCommentRepository implements ICommentRepository {

    private List<Comment> comments = new ArrayList<>();

    private Comment createTestData(Long id) {
        Member member = Member.builder()
                .email("test1@example.com")
                .nickName("test user 1")
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);

        Post post = Post.builder()
                .member(member)
                .title("title 1")
                .content("content 1")
                .build();
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(String.format("comment %d", id))
                .build();
        ReflectionTestUtils.setField(comment, "id", id);
        return comment;
    }

    public FakeCommentRepository() {
        for (long i=1; i <= 3; i++) {
            Comment comment = createTestData(i);
            comments.add(comment);
        }
    }

    @Override
    public Page<Comment> findAllByPost(Long postId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Comment> comments = this.comments.stream().filter(comment -> Objects.equals(comment.getPost().getId(), postId)).toList();
        List<Comment> data = new ArrayList<>(comments.subList(offset, Math.min(offset + pageSize, comments.size())));
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.of(comments.get(id.intValue() - 1));
    }

    @Override
    public Comment save(Comment comment) {
        ReflectionTestUtils.setField(comment, "id", comments.size() + 1L);
        comments.add(comment);
        return comment;
    }
}
