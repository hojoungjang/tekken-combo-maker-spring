package com.github.hojoungjang.tekken_combo_maker.member.service;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.List;

class MemberServiceTest {

    private final MemberService memberService = new MemberService(
            new FakeMemberRepository(),
            NoOpPasswordEncoder.getInstance()
    );

    @DisplayName("ID 를 이용해 특정 멤버를 가져올 수 있다.")
    @Test
    public void ID_를_이용해_특정_멤버를_가져올_수_있다() throws Exception {
        // given
        Long memberId = 1L;
        
        // when
        MemberResponse member = memberService.findById(memberId);

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
                    memberService.findById(memberId);
                })
                .withMessageContaining("Member not found with ID: 10");
    }

    @DisplayName("email 을 이용해 특정 멤버를 가져올 수 있다.")
    @Test
    public void email_을_이용해_특정_멤버를_가져올_수_있다() throws Exception {
        // given
        String email = "test1@example.com";

        // when
        MemberResponse member = memberService.findByEmail(email);

        // then
        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(member.getEmail()).isEqualTo("test1@example.com");
        Assertions.assertThat(member.getNickname()).isEqualTo("test user 1");
    }

    @DisplayName("email 을 이용해 매칭되는 멤버가 없으면 NotFoundException 예외를 던지다.")
    @Test
    public void email_이_매칭되지_않으면_멤버를_가져올_수_없다() throws Exception {
        // given
        String email = "test10@example.com";

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    memberService.findByEmail(email);
                })
                .withMessageContaining("Member not found with email: test10@example.com");
    }

    @DisplayName("모든 멤버를 가져올 수 있다.")
    @Test
    public void 모든_멤버를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MemberResponse> memberPage = memberService.findAll(pageable);

        // then
        List<MemberResponse> members = memberPage.getContent();
        Assertions.assertThat(members).isNotEmpty().hasSize(3);
        Assertions.assertThat(members)
                .extracting(MemberResponse::getId)
                .contains(1L, 2L, 3L);
        Assertions.assertThat(members)
                .extracting(MemberResponse::getEmail)
                .contains("test1@example.com", "test1@example.com", "test1@example.com");
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
        Long memberId = memberService.create(request);

        // then
        MemberResponse member = memberService.findById(memberId);
        Assertions.assertThat(member.getEmail()).isEqualTo("testuser@example.com");
        Assertions.assertThat(member.getNickname()).isEqualTo("Test User");
    }
}
