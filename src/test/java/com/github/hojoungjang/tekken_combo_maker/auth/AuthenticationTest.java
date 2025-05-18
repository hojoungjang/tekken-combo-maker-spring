package com.github.hojoungjang.tekken_combo_maker.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("유효하지 않은 사용자 로그인 정보를 이용해 로그인 할 수 없다.")
    @Test
    public void loginWithInvalidUser() throws Exception {
        mvc.perform(formLogin().user("testuser").password("password"))
                .andExpect(unauthenticated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.title").value("Unauthorized"))
                .andExpect(jsonPath("$.error.detail").value("Full authentication is required to access this resource"));
    }

    // TODO: Need a way to test a valid user (successful scenario)
    @DisplayName("유효한 사용자 로그인 정보를 이용해 로그인 할 수 있다.")
    @Test
    public void loginWithValidUser() throws Exception {
    }
}
