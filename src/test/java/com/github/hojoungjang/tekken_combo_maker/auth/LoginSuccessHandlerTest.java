package com.github.hojoungjang.tekken_combo_maker.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

class LoginSuccessHandlerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("비밀번호를 로그인 케이스")
    @Test
    public void passwordLoginTest() throws Exception {
        // given
        LoginSuccessHandler successHandler = new LoginSuccessHandler(objectMapper);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(
                "testuser@example.com",
                "password",
                List.of(new SimpleGrantedAuthority("user"))
        );

        // when
        successHandler.onAuthenticationSuccess(request, response, token);

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getContentType()).isEqualTo("application/json");
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        Assertions.assertThat(jsonNode.get("data").asText())
                .isEqualTo("Successfully logged in as testuser@example.com");
    }

    @DisplayName("OAuth 로그인 케이스")
    @Test
    public void oauthLoginTest() throws Exception {
        // given
        LoginSuccessHandler successHandler = new LoginSuccessHandler(objectMapper);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("user"));
        OAuth2User user = new DefaultOAuth2User(
                grantedAuthorities,
                Map.of("email", "testuser@example.com"),
                "email"
        );
        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(user, grantedAuthorities, "test");

        // when
        successHandler.onAuthenticationSuccess(request, response, token);

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getContentType()).isEqualTo("application/json");
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        Assertions.assertThat(jsonNode.get("data").asText())
                .isEqualTo("Successfully logged in as testuser@example.com");
    }
}