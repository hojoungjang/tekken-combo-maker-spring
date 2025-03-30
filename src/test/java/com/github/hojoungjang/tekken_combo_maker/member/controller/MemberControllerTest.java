package com.github.hojoungjang.tekken_combo_maker.member.controller;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateResponse;
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
        Assertions.assertThat(member.getNickname()).isEqualTo("test user 1");
    }

    @DisplayName("ID 가 매칭되는 멤버가 존재하지 않으면 NotFoundException 예외를 던지다.")
    @Test
    public void ID_가_매칭되지_않으면_멤버를_가져올_수_없다() throws Exception {
        // given
        Long memberId = 10L;

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    memberController.getById(memberId);
                })
                .withMessageContaining("Member not found with ID: 10");
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
                .extracting(MemberResponse::getNickname)
                .contains("test user 1", "test user 2", "test user 3");
    }

    @DisplayName("멤버를 생성 할 수 있다.")
    @Test
    public void 멤버를_생성_할_수_있다() throws Exception {
        // given
        String email = "testuser@example.com";
        String password = "password";
        String nickname = "Test User";
        MemberCreateRequest request = new MemberCreateRequest(email, password, nickname);

        // when
        MemberCreateResponse response = memberController.create(request);

        // then
        MemberResponse member = memberController.getById(response.getId());
        Assertions.assertThat(member.getEmail()).isEqualTo("testuser@example.com");
        Assertions.assertThat(member.getNickname()).isEqualTo("Test User");
    }
}
