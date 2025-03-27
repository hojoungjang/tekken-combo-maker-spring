package com.github.hojoungjang.tekken_combo_maker.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final boolean success;  // TODO: default to true?
    private final T data;

    @Builder
    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
