package com.github.hojoungjang.tekken_combo_maker.post.mock;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import com.github.hojoungjang.tekken_combo_maker.post.service.IPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakePostRepository implements IPostRepository {

    private List<Post> posts = new ArrayList<>();

    private Post createTestData(Long id) {
        Member member = Member.builder()
                .email(String.format("test%d@example.com", id))
                .nickname(String.format("test user %d", id))
                .build();
        ReflectionTestUtils.setField(member, "id", id);

        Post post = Post.builder()
                .member(member)
                .title(String.format("title %d", id))
                .content(String.format("content %d", id))
                .build();
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }

    public FakePostRepository() {
        Post post = createTestData(posts.size() + 1L);
        posts.add(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        int idx = id.intValue() - 1;
        if (idx < 0 || idx >= posts.size()) {
            return Optional.empty();
        }
        return Optional.of(posts.get(idx));
    }

    @Override
    public Page<Post> findByMember(Long memberId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Post> memberPosts = posts.stream().filter(post -> Objects.equals(post.getMember().getId(), memberId)).toList();
        List<Post> data = new ArrayList<>(memberPosts.subList(offset, Math.min(offset + pageSize, memberPosts.size())));
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Post> data = new ArrayList<>(posts.subList(offset, Math.min(offset + pageSize, posts.size())));
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public Post create(Post post) {
        ReflectionTestUtils.setField(post, "id", posts.size() + 1L);
        posts.add(post);
        return post;
    }
}
