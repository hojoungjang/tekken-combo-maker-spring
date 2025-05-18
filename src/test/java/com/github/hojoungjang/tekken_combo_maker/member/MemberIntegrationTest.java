package com.github.hojoungjang.tekken_combo_maker.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.member.dto.MemberCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("멤버 생성 요청시 RequestBody 유효성 확인")
    @Test
    public void validateMemberCreateRequest() throws Exception {
        // given
        String email = "testuser@example.com";
        String nickname = "Test User";
        MemberCreateRequest request = new MemberCreateRequest(email, null, nickname);
        String content = objectMapper.writeValueAsString(request);

        // when
        // then
//        mockMvc.perform(post("/api/v1/members")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error.detail").value("Invalid request content."));
        mockMvc.perform(post("/api/v1/members")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.detail").value("Full authentication is required to access this resource"));
    }
}
