package com.github.hojoungjang.tekken_combo_maker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String exceptionMsg = exception.getMessage();
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            exceptionMsg = oAuth2AuthenticationException.getError().getErrorCode();
        }
        log.error(String.format("Authentication Failed: %s", exceptionMsg));

        ProblemDetail errorBody = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exceptionMsg);
        BaseErrorResponse responseBody = BaseErrorResponse.builder()
                .success(false)
                .error(errorBody)
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        PrintWriter writer = response.getWriter();
        writer.write(objectWriter.writeValueAsString(responseBody));
    }
}
