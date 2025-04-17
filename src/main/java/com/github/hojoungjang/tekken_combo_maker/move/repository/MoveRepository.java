package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MoveRepository implements IMoveRepository {

    private final MoveMongoRepository moveMongoRepository;
    private final MoveQueryRepository moveQueryRepository;

    public Page<Move> findAllByCharacter(Long characterId, Pageable pageable) {
        return moveMongoRepository.findAllByCharacterId(characterId, pageable);
    }

    @Override
    public Page<Move> findAll(MoveSearchRequest request, Pageable pageable) {

//        Move moveSearch = Move.builder()
//                .characterId(request.getCharacterId())
//                .moveCategory(request.getMoveCategory())
//                .build();
//        Example<Move> moveExample = Example.of(moveSearch);

//        Page<Move> moves = moveMongoRepository.findAll(moveExample, pageable);
        Page<Move> moves = moveQueryRepository.findAll(request, pageable);
        return moves;
    }
}
