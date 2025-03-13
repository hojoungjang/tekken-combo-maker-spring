package com.github.hojoungjang.tekken_combo_maker.member.service;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IMemberRepository {

    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Page<Member> findAll(Pageable pageable);
}
