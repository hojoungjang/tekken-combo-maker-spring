package com.github.hojoungjang.tekken_combo_maker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String msg = String.format("Successfully logged in as %s", authentication.getName());
        log.info(msg);

        // TODO: 이런 방식도 가능하다.
        // response.sendRedirect("/api/v1/auth/login/success");

        BaseResponse<Object> responseBody = BaseResponse.builder()
                .success(true)
                .data(msg)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        PrintWriter writer = response.getWriter();
        writer.write(objectWriter.writeValueAsString(responseBody));
    }
}
