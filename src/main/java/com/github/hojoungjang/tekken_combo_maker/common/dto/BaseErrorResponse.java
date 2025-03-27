package com.github.hojoungjang.tekken_combo_maker.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ProblemDetail;

@Getter
public class BaseErrorResponse {
    private final boolean success;      // TODO: default to false?
    private final ProblemDetail error;

    @Builder
    BaseErrorResponse(boolean success, ProblemDetail error) {
        this.success = success;
        this.error = error;
    }
}
