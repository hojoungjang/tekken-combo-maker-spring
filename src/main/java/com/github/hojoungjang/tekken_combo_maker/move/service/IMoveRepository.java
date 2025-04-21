package com.github.hojoungjang.tekken_combo_maker.move.service;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMoveRepository {

    Page<Move> findAllByCharacter(Long characterId, Pageable pageable);

    Page<Move> findAll(MoveSearchRequest request, Pageable pageable);
}
