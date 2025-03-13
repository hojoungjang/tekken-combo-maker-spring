package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService memberService;

    @GetMapping("/{id}")
    public MemberResponse getById(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @GetMapping
    public Page<MemberResponse> getAll(Pageable pageable) {
        return memberService.findAll(pageable);
    }
}
