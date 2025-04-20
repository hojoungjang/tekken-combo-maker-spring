package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.github.hojoungjang.tekken_combo_maker.move.model.document.QMove.move;

@Repository
@RequiredArgsConstructor
public class MoveRepository implements IMoveRepository {

    private final MoveMongoRepository moveMongoRepository;

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

        BooleanBuilder predicate = new BooleanBuilder();

        if (request.getCharacterId() != null) {
            predicate.and(move.characterId.eq(request.getCharacterId()));
        }
        if (request.getStartupFrameStart() != null || request.getStartupFrameEnd() != null) {
            predicate.and(move.startupFrame.between(request.getStartupFrameStart(), request.getStartupFrameEnd()));
        }
        if (request.getMoveAttributes() != null && !request.getMoveAttributes().isEmpty()) {
            BooleanBuilder moveAttributesPredicate = new BooleanBuilder();
            request.getMoveAttributes().forEach(moveAttribute -> {
                moveAttributesPredicate.and(move.moveAttributes.contains(moveAttribute));
            });
            predicate.and(moveAttributesPredicate);
        }
        if (request.getMoveCategory() != null) {
            predicate.and(move.moveCategory.eq(request.getMoveCategory()));
        }

        List<Move> moves = new ArrayList<>();
        moveMongoRepository.findAll(predicate).forEach(moves::add);
        long total = moveMongoRepository.count(predicate);
//        Page<Move> moves = moveQueryRepository.findAll(request, pageable);
        return new PageImpl<>(moves, pageable, total);
    }
}
