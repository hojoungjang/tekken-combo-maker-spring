package com.github.hojoungjang.tekken_combo_maker.common.exception.handler;

import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(NoResourceFoundException.class)
//    public ResponseEntity<String> handleException(
//            NoResourceFoundException ex
//    ) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("hello");
//    }

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(
//            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
//        String newBody = "hello";
//        return super.handleExceptionInternal(ex, newBody, headers, statusCode, request);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }
}
