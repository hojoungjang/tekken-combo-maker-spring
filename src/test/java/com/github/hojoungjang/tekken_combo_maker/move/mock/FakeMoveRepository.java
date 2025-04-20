package com.github.hojoungjang.tekken_combo_maker.move.mock;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.CleanHitInfo;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import com.github.hojoungjang.tekken_combo_maker.move.service.IMoveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FakeMoveRepository implements IMoveRepository {

    private List<Move> moves = new ArrayList<>();

    private Move createTestData(String id) {
        CleanHitInfo cleanHitInfo = CleanHitInfo.builder()
                .damages(List.of(11))
                .hitFrame(15)
                .hitFrameWake(5)
                .hitStatus(HitStatus.DOWN)
                .build();

        Move move = Move.builder()
                .characterId(1L)
                .stanceName("test stance")
                .name("move " + id)
                .command("command " + id)
                .commandDescription("command description " + id)
                .damages(List.of(10))
                .hitCount(1)
                .counter(false)
                .startupFrame(10)
                .hitLevels(List.of(HitLevel.HIGH))
                .guardFrame(10)
                .hitFrame(10)
                .hitFrameWake((-10))
                .hitFrameEngager(17)
                .hitStatus(HitStatus.DOWN)
                .cleanHitInfo(cleanHitInfo)
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

    @Override
    public Page<Move> findAll(MoveSearchRequest request, Pageable pageable) {
        List<Move> searchMoves = new ArrayList<>(moves);

        if (request.getCharacterId() != null) {
            List<Move> lll = searchMoves.stream().filter(
                    move -> move.getCharacterId().equals(request.getCharacterId())
            ).toList();
            List<Integer> asd = List.of(1);
        }
        if (StringUtils.hasText(request.getNameSearch())) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getName().toLowerCase().startsWith(request.getNameSearch().toLowerCase())
            ).toList();
        }
        if (request.getCounter() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.isCounter() == request.getCounter()
            ).toList();
        }
        if (request.getStartupFrameStart() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getStartupFrame() >= request.getStartupFrameStart()
            ).toList();
        }
        if (request.getStartupFrameEnd() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getStartupFrame() <= request.getStartupFrameEnd()
            ).toList();
        }
        if (request.getHitLevel() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getHitLevels().getFirst().equals(request.getHitLevel())
            ).toList();
        }
        if (request.getGuardFrameStart() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getGuardFrame() >= request.getGuardFrameStart()
            ).toList();
        }
        if (request.getGuardFrameEnd() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getGuardFrame() <= request.getGuardFrameEnd()
            ).toList();
        }
        if (request.getMoveAttributes() != null && !request.getMoveAttributes().isEmpty()) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getMoveAttributes().containsAll(request.getMoveAttributes())
            ).toList();
        }
        if (request.getMoveCategory() != null) {
            searchMoves = searchMoves.stream().filter(
                    move -> move.getMoveCategory().equals(request.getMoveCategory())
            ).toList();
        }

        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        List<Move> pagedSearchMoves = new ArrayList<>(searchMoves.subList(offset, Math.min(offset + pageSize, searchMoves.size())));
        return new PageImpl<>(pagedSearchMoves, pageable, searchMoves.size());
    }
}
