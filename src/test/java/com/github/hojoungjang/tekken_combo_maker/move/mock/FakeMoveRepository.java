package com.github.hojoungjang.tekken_combo_maker.move.mock;

import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import org.aspectj.util.Reflection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FakeMoveRepository implements IMoveRepository {

    private List<Move> moves = new ArrayList<>();

    private Move createTestData(String id) {
        Move move = Move.builder()
                .characterId(1L)
                .stanceName("test stance")
                .name("move " + id)
                .command("command " + id)
                .damages(List.of(10))
                .hitCount(1)
                .counter(false)
                .startupFrame(10)
                .hitLevels(List.of(HitLevel.HIGH))
                .hitFrame(10)
                .guardFrame(10)
                .moveAttributes(Set.of())
                .moveCategory(MoveCategory.NORMAL)
                .build();
        ReflectionTestUtils.setField(move, "id", id);
        return move;
    }

    public FakeMoveRepository() {
        for (int i=1; i <= 5; i++) {
            moves.add(createTestData(String.valueOf(i)));
        }
    }

    @Override
    public Page<Move> findAllByCharacter(Long characterId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Move> characterMoves = this.moves.stream().filter(move -> move.getCharacterId().equals(characterId)).toList();
        List<Move> data = new ArrayList<>(characterMoves.subList(offset, Math.min(offset + pageSize, characterMoves.size())));
        return new PageImpl<>(data, pageable, data.size());
    }
}
