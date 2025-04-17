package com.github.hojoungjang.tekken_combo_maker.move.controller;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMoveService {

    Page<MoveResponse> findAllByCharacter(Long characterId, Pageable pageable);

    Page<MoveResponse> findAll(MoveSearchRequest request, Pageable pageable);
}
