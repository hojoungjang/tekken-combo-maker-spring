package com.github.hojoungjang.tekken_combo_maker.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;

public class NotFoundException extends ErrorResponseException implements ErrorResponse {
    public NotFoundException(HttpStatusCode status) {
        super(status);
    }

    public NotFoundException(HttpStatusCode status, Throwable cause) {
        super(status, cause);
    }

    public NotFoundException(HttpStatusCode status, ProblemDetail body, Throwable cause) {
        super(status, body, cause);
    }

    public NotFoundException(HttpStatusCode status, ProblemDetail body, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, body, cause, messageDetailCode, messageDetailArguments);
    }

    public static NotFoundException supplier(String detail) {
        return supplier(detail, null);
    }

    public static NotFoundException supplier(String detail, Throwable cause) {
        return new NotFoundException(
                HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()),
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail),
                cause
        );
    }
}
