package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoveQueryRepository {

    Page<Move> findAll(MoveSearchRequest request, Pageable pageable);
}
