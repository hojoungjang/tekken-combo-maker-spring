package com.github.hojoungjang.tekken_combo_maker.member.mock;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.controller.IMemberService;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.service.IMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FakeMemberService implements IMemberService {

    private final IMemberRepository memberRepository = new FakeMemberRepository();
    private final PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    @Override
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> NotFoundException.supplier(
                        String.format("Member not found with ID: %d", id)
                ));
        return MemberResponse.fromEntity(member);
    }

    @Override
    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> NotFoundException.supplier(String.format("Member not found with email: %s", email)));
        return MemberResponse.fromEntity(member);
    }

    @Override
    public Page<MemberResponse> findAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberResponse::fromEntity);
    }

    @Override
    public Long create(MemberCreateRequest request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();
        return memberRepository.save(member);
    }
}
