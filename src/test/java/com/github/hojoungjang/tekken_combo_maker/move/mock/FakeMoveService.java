package com.github.hojoungjang.tekken_combo_maker.move.mock;

import com.github.hojoungjang.tekken_combo_maker.move.controller.IMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakeMoveService implements IMoveService {

    private final IMoveRepository moveRepository = new FakeMoveRepository();

    @Override
    public Page<MoveResponse> findAllByCharacter(Long characterId, Pageable pageable) {
        Page<Move> characterMovePage = moveRepository.findAllByCharacter(characterId, pageable);
        return characterMovePage.map(MoveResponse::fromEntity);
    }
}
