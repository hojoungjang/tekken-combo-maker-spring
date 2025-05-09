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
import org.springframework.util.StringUtils;

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

    /**
     * 기술 검색 기능
     * - 이름
     * - 카운터 여부
     * - 발동 프레임 범위 (이때 startupFrames 의 첫번째 원소)
     * - 타점 판정 (hitLevels: low mid high)
     * - 가드 프레임 범위
     * - 특수 효과 (moveAttributes)
     * - 기술 분류 (moveCategory)
     */
    @Override
    public Page<Move> findAll(MoveSearchRequest request, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (request.getCharacterId() != null) {
            predicate.and(move.characterId.eq(request.getCharacterId()));
        }
        if (StringUtils.hasText(request.getNameSearch())) {
            predicate.and(move.name.startsWithIgnoreCase(request.getNameSearch()));
        }
        if (request.getCounter() != null) {
            predicate.and(move.counter.eq(request.getCounter()));
        }
        if (request.getStartupFrameStart() != null || request.getStartupFrameEnd() != null) {
            predicate.and(move.startupFrame.between(request.getStartupFrameStart(), request.getStartupFrameEnd()));
        }
        if (request.getHitLevels() != null) {
            predicate.and(move.hitLevels.get(0).in(request.getHitLevels()));
        }
        if (request.getGuardFrameStart() != null || request.getGuardFrameEnd() != null) {
            predicate.and(move.guardFrame.between(request.getGuardFrameStart(), request.getGuardFrameEnd()));
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
        moveMongoRepository.findAll(predicate, pageable).forEach(moves::add);
        long total = moveMongoRepository.count(predicate);
        return new PageImpl<>(moves, pageable, total);
    }
}
