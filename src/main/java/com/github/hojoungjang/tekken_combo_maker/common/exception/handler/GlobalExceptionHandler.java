package com.github.hojoungjang.tekken_combo_maker.common.exception.handler;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(
        NotFoundException ex,  WebRequest request
    ) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, ex.getBody(), ex.getHeaders(), ex.getStatusCode(), request);
    }
}
