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
     * - 일단 기본적으로 특정 캐릭터는 잡고 들어간다고 보는게 유즈케이스의 90% 이상일 것이다.
     *   때문에 추가로 전체 기술풀에서 검색은 지원이 없을것 같다.
     *   그렇다면 캐릭터 기술에서 필요한 검색 파라미터는 다음정도가 된다.
     * - 이름
     * - 카운터 여부
     * - 발동 프레임 범위 (이때 startupFrames 의 첫번째 원소)
     * - 타점 판정 (hitLevels : low mid high)
     * - 가드 프레임 범위
     * - 특수 효과 (moveAttributes)
     * - 기술 분류 (moveCategory)
     *
     * @param request
     * @param pageable
     * @return
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
        if (request.getHitLevel() != null) {
            predicate.and(move.hitLevels.get(0).eq(request.getHitLevel()));
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
        moveMongoRepository.findAll(predicate).forEach(moves::add);
        long total = moveMongoRepository.count(predicate);
        return new PageImpl<>(moves, pageable, total);
    }
}
