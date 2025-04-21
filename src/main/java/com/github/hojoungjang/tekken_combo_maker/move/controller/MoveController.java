package com.github.hojoungjang.tekken_combo_maker.move.controller;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/moves")
@RequiredArgsConstructor
public class MoveController {

    private final IMoveService moveService;

    @GetMapping
    public Page<MoveResponse> searchAll(MoveSearchRequest request, Pageable pageable) {
        return moveService.findAll(request, pageable);
    }
}
