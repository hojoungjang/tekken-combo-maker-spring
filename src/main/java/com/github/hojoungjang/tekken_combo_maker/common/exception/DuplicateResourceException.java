package com.github.hojoungjang.tekken_combo_maker.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;

public class DuplicateResourceException extends ErrorResponseException implements ErrorResponse {
    public DuplicateResourceException(HttpStatusCode status) {
        super(status);
    }

    public DuplicateResourceException(HttpStatusCode status, Throwable cause) {
        super(status, cause);
    }

    public DuplicateResourceException(HttpStatusCode status, ProblemDetail body, Throwable cause) {
        super(status, body, cause);
    }

    public DuplicateResourceException(HttpStatusCode status, ProblemDetail body, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, body, cause, messageDetailCode, messageDetailArguments);
    }

    public static DuplicateResourceException supplier(String detail) {
        return supplier(detail, null);
    }

    public static DuplicateResourceException supplier(String detail, Throwable cause) {
        return new DuplicateResourceException(
                HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()),
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detail),
                cause
        );
    }
}
