package com.github.hojoungjang.tekken_combo_maker.member.mock;

import com.github.hojoungjang.tekken_combo_maker.member.controller.IMemberService;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.service.IMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class FakeMemberService implements IMemberService {

    private final IMemberRepository memberRepository = new FakeMemberRepository();

    @Override
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).get();
        return MemberResponse.fromEntity(member);
    }

    @Override
    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return MemberResponse.fromEntity(member);
    }

    @Override
    public Page<MemberResponse> findAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberResponse::fromEntity);
    }
}
