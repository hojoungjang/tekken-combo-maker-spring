package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

class MemberControllerTest {

    private MemberController memberController = new MemberController(new FakeMemberService());

    @DisplayName("ID 를 사용헤 멤버 정보를 가져올 수 있다.")
    @Test
    public void ID_를_사용헤_멤버_정보를_가져올_수_있다() {
        // given
        Long memberId = 1L;

        // when
        MemberResponse member = memberController.getById(memberId);

        // then
        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(member.getEmail()).isEqualTo("test1@example.com");
        Assertions.assertThat(member.getNickName()).isEqualTo("test user 1");
    }

    @DisplayName("여러 멤버 정보를 가져올 수 있다.")
    @Test
    public void 여러_멤버_정보를_가져올_수_있다() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MemberResponse> memberPage = memberController.getAll(pageable);

        // then
        List<MemberResponse> members = memberPage.getContent();
        Assertions.assertThat(members).isNotEmpty().hasSize(3);
        Assertions.assertThat(members)
                .extracting(MemberResponse::getId)
                .contains(1L, 2L, 3L);
        Assertions.assertThat(members)
                .extracting(MemberResponse::getEmail)
                .contains("test1@example.com", "test2@example.com", "test3@example.com");
        Assertions.assertThat(members)
                .extracting(MemberResponse::getNickName)
                .contains("test user 1", "test user 2", "test user 3");
    }
}
