package com.github.hojoungjang.tekken_combo_maker.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ProblemDetail;

@Getter
public class BaseResponse<T> {
    private final boolean success;
    private final T data;

    @Builder
    BaseResponse(boolean success, T data, ProblemDetail error) {
        this.success = success;
        this.data = data;
    }
}
