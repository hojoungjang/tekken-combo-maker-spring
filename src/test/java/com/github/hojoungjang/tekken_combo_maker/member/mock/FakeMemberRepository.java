package com.github.hojoungjang.tekken_combo_maker.member.mock;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.service.IMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeMemberRepository implements IMemberRepository {

    private List<Member> members = new ArrayList<>();

    private Member createTestData(Long id) {
        Member member = Member.builder()
                .email(String.format("test%d@example.com", id))
                .nickName(String.format("test user %d", id))
                .build();
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    public FakeMemberRepository() {
        for (long i=1; i <= 3; i++) {
            members.add(createTestData(i));
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.of(members.get(id.intValue() - 1));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        List<Member> match = members.stream().filter(member -> member.getEmail().equals(email)).toList();
        if (match.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(match.getFirst());
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Member> data = new ArrayList<>(members.subList(offset, Math.min(offset + pageSize, members.size())));
        return new PageImpl<>(data, pageable, data.size());
    }
}
