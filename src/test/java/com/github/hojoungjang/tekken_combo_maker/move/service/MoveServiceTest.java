package com.github.hojoungjang.tekken_combo_maker.move.service;

import com.github.hojoungjang.tekken_combo_maker.move.controller.IMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.CleanHitInfoResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
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

    @DisplayName("캐릭터 ID 를 (characterId) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_캐릭터_ID() throws Exception {
        // given
        Long characterId = 1L;
        MoveSearchRequest request = MoveSearchRequest.builder().characterId(1L).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> movePage = moveService.findAll(request, pageable);

        // then
        List<MoveResponse> content = movePage.getContent();
        Assertions.assertThat(content).isNotEmpty().hasSize(5);
        Assertions.assertThat(content)
                .extracting(MoveResponse::getCharacterId)
                .containsExactlyInAnyOrder(1L, 1L, 1L, 1L, 1L);
    }

    @DisplayName("기술 이름을 (name) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_기술_이름() throws Exception {

    }

    @DisplayName("기술 카운터 여부를 (counter) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_카운터_여부D() throws Exception {

    }

    @DisplayName("발동 프레임을 (startupFrame) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_발동_프레임() throws Exception {

    }

    @DisplayName("타점 판정을 (hitLevel) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_타점_판정() throws Exception {

    }

    @DisplayName("가드 프레임을 (guardFrame) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_가드_프레임() throws Exception {

    }

    @DisplayName("기술 특수효과를 (moveAttributes) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_기술_특수효과() throws Exception {

    }

    @DisplayName("기술 분류를 (moveCategory) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_기술_분류() throws Exception {

    }
}