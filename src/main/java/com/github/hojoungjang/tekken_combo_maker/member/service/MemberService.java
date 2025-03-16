package com.github.hojoungjang.tekken_combo_maker.member.service;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.controller.IMemberService;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final IMemberRepository memberRepository;

    @Override
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> NotFoundException.supplier(String.format("Member not found with ID: %d", id)));
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
}
