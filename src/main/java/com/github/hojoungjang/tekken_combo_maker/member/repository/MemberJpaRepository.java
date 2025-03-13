package com.github.hojoungjang.tekken_combo_maker.member.repository;

import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
