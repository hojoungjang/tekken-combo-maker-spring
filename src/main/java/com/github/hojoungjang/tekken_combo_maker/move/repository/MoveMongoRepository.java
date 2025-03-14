package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoveMongoRepository extends MongoRepository<Move, String> {
}
