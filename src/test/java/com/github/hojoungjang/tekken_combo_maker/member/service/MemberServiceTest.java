package com.github.hojoungjang.tekken_combo_maker.member.service;

import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberResponse;
import com.github.hojoungjang.tekken_combo_maker.member.mock.FakeMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    private MemberService memberService = new MemberService(new FakeMemberRepository());

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
        Assertions.assertThat(member.getNickName()).isEqualTo("test user 1");
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
        Assertions.assertThat(member.getNickName()).isEqualTo("test user 1");
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
                .extracting(MemberResponse::getNickName)
                .contains("test user 1", "test user 2", "test user 3");
    }
}