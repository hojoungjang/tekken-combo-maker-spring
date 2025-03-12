package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IMemberService {

    MemberResponse findById(Long id);
    MemberResponse findByEmail(String email);
    Page<MemberResponse> findAll(Pageable pageable);
}
