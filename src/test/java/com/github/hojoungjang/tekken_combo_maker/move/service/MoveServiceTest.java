package com.github.hojoungjang.tekken_combo_maker.move.service;

import com.github.hojoungjang.tekken_combo_maker.move.controller.IMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.CleanHitInfoResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.mock.FakeMoveRepository;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

class MoveServiceTest {

    private final IMoveService moveService = new MoveService(new FakeMoveRepository());

    @DisplayName("캐릭터 별로 기술을 가져 올 수 있다.")
    @Test
    public void 캐릭터_별로_기술을_가져_올_수_있다() throws Exception {
        // given
        Long characterId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> movePage = moveService.findAllByCharacter(characterId, pageable);

        // then
        List<MoveResponse> moves = movePage.getContent();
        Assertions.assertThat(moves).hasSize(5);
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getId)
                .containsExactlyInAnyOrder("1", "2", "3", "4", "5");
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getName)
                .containsExactlyInAnyOrder("move 1", "move 2", "move 3", "move 4", "move 5");
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getCommand)
                .containsExactlyInAnyOrder("command 1", "command 2", "command 3", "command 4", "command 5");
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getCommandDescription)
                .containsExactlyInAnyOrder(
                        "command description 1",
                        "command description 2",
                        "command description 3",
                        "command description 4",
                        "command description 5"
                );

        Assertions.assertThat(moves).first()
                .extracting(MoveResponse::getCleanHitInfo)
                .isEqualTo(CleanHitInfoResponse.builder()
                        .damages(List.of(11))
                        .hitFrame(15)
                        .hitFrameWake(5)
                        .hitStatus(HitStatus.DOWN)
                        .build());
    }
}