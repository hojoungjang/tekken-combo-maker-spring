package com.github.hojoungjang.tekken_combo_maker.move.service;

import com.github.hojoungjang.tekken_combo_maker.move.controller.IMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveService implements IMoveService {

    private final IMoveRepository moveRepository;

    @Override
    public Page<MoveResponse> findAllByCharacter(Long characterId, Pageable pageable) {
        Page<Move> characterMovePage = moveRepository.findAllByCharacter(characterId, pageable);
        return characterMovePage.map(MoveResponse::fromEntity);
    }
}
