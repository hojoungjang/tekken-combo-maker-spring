package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.github.hojoungjang.tekken_combo_maker.move.model.document.QMove.move;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveRepositoryTest {

    @Mock
    private MoveMongoRepository moveMongoRepository;

    private MoveRepository moveRepository;

    @BeforeEach
    void setUp() {
        moveRepository = new MoveRepository(moveMongoRepository);
    }

    @Test
    public void test() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        Long characterId = 1L;
        String nameSearch = "name";
        Boolean counter = false;
        Integer startupFrameStart = 10;
        Integer startupFrameEnd = 15;
        List<HitLevel> hitLevels = List.of(HitLevel.MID);
        Integer guardFrameStart = 10;
        Integer guardFrameEnd = 15;
        List<MoveAttribute> moveAttributes = List.of(MoveAttribute.POWER_CRUSH);
        MoveCategory moveCategory = MoveCategory.NORMAL;

        MoveSearchRequest request = MoveSearchRequest.builder()
                .characterId(characterId)
                .nameSearch(nameSearch)
                .counter(counter)
                .startupFrameStart(startupFrameStart)
                .startupFrameEnd(startupFrameEnd)
                .hitLevels(hitLevels)
                .guardFrameStart(guardFrameStart)
                .guardFrameEnd(guardFrameEnd)
                .moveAttributes(moveAttributes)
                .moveCategory(moveCategory)
                .build();

        BooleanBuilder predicate = new BooleanBuilder();
        predicate
                .and(move.characterId.eq(characterId))
                .and(move.name.startsWithIgnoreCase(nameSearch))
                .and(move.counter.eq(counter))
                .and(move.startupFrame.between(startupFrameStart, startupFrameEnd))
                .and(move.hitLevels.get(0).in(hitLevels.getFirst()))
                .and(move.guardFrame.between(guardFrameStart, guardFrameEnd))
                .and(move.moveAttributes.contains(moveAttributes.getFirst()))
                .and(move.moveCategory.eq(moveCategory));

        when(moveMongoRepository.findAll(predicate, pageable)).thenReturn(Page.empty());

        // when
        Page<Move> movePage = moveRepository.findAll(request, pageable);

        // then
        verify(moveMongoRepository).findAll(predicate, pageable);
        verify(moveMongoRepository).count(predicate);
    }
}
