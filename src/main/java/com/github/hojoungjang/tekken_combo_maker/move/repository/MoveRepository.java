package com.github.hojoungjang.tekken_combo_maker.move.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MoveRepository {

    private final MoveMongoRepository moveMongoRepository;

    public void findAllByCharacter(Long characterId) {

    }
}
