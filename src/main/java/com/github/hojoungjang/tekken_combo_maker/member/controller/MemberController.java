package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateResponse;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController implements SwaggerMemberController {

    private final IMemberService memberService;

    @GetMapping("/{id}")
    public MemberResponse getById(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @GetMapping
    public Page<MemberResponse> getAll(Pageable pageable) {
        return memberService.findAll(pageable);
    }

    @PostMapping
    public MemberCreateResponse create(@Valid @RequestBody MemberCreateRequest request) {
        Long id = memberService.create(request);
        return MemberCreateResponse.builder()
                .id(id)
                .build();
    }
}
