package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MoveMongoRepository extends MongoRepository<Move, String>, QuerydslPredicateExecutor<Move> {

    Page<Move> findAllByCharacterId(Long characterId, Pageable pageable);
}
