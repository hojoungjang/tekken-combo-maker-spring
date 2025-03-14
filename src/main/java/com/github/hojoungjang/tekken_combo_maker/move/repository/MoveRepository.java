package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MoveRepository {

    private final MoveMongoRepository moveMongoRepository;

    public Page<Move> findAllByCharacter(Long characterId, Pageable pageable) {
        return moveMongoRepository.findAllByCharacter(characterId, pageable);
    }
}
