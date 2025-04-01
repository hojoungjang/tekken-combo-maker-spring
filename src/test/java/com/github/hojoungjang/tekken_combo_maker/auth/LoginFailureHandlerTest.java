package com.github.hojoungjang.tekken_combo_maker.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

class LoginFailureHandlerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("비밀번호 로그인 케이스")
    @Test
    public void badPasswordLoginTest() throws Exception {
        // given
        LoginFailureHandler failureHandler = new LoginFailureHandler(objectMapper);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BadCredentialsException exception = new BadCredentialsException("Test for bad password login");

        // when
        failureHandler.onAuthenticationFailure(request, response, exception);

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(401);
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        Assertions.assertThat(jsonNode.get("error").get("detail").asText())
                .isEqualTo("Test for bad password login");
    }

    @DisplayName("OAuth 로그인 케이스")
    @Test
    public void badOAuthLoginTest() throws Exception {
        // given
        LoginFailureHandler failureHandler = new LoginFailureHandler(objectMapper);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        OAuth2AuthenticationException exception = new OAuth2AuthenticationException("Test for bad OAuth login");

        // when
        failureHandler.onAuthenticationFailure(request, response, exception);

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(401);
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        Assertions.assertThat(jsonNode.get("error").get("detail").asText())
                .isEqualTo("Test for bad OAuth login");
    }
}