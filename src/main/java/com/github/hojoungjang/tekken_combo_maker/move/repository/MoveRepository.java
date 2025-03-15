package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MoveRepository implements IMoveRepository {

    private final MoveMongoRepository moveMongoRepository;

    public Page<Move> findAllByCharacter(Long characterId, Pageable pageable) {
        return moveMongoRepository.findAllByCharacterId(characterId, pageable);
    }
}
