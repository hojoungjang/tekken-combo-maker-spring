package com.github.hojoungjang.tekken_combo_maker.move.service;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMoveRepository {

    Page<Move> findAllByCharacter(Long characterId, Pageable pageable);

    Page<Move> findAll(MoveSearchRequest request, Pageable pageable);
    /**
     * 검색 기능이 필요
     * findAll() 인데 이제 여러 검색 파라미터를 곁들인
     * - 일단 기본적으로 특정 캐릭터는 잡고 들어간다고 보는게 유즈케이스의 90% 이상일 것이다.
     *   때문에 추가로 전체 기술풀에서 검색은 지원이 없을것 같다.
     *   그렇다면 캐릭터 기술에서 필요한 검색 파라미터는 다음정도가 된다.
     * - 이름
     * - 커맨드 값 (?) maybe, maybe not
     * - 카운터 여부
     * - 발동 프레임 범위 (이때 startupFrames 의 첫번째 원소)
     * - 타점 판정 (hitLevels : low mid high)
     * - 가드 프레임 범위
     * - 특수 효과 (moveAttributes)
     * - 기술 분류 (moveCategory)
     */
}
