package com.github.hojoungjang.tekken_combo_maker.move.controller;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.mock.FakeMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.move.model.enums.MoveCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public class MoveControllerTest {
    
    private final MoveController moveController = new MoveController(new FakeMoveService());

    @DisplayName("캐릭터 ID 를 (characterId) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_캐릭터_ID() throws Exception {
        // given
        MoveSearchRequest request = MoveSearchRequest.builder().characterId(1L).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> movePage = moveController.searchAll(request, pageable);

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
        // given
        MoveSearchRequest request = MoveSearchRequest.builder().nameSearch("move 1").build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> movePage = moveController.searchAll(request, pageable);

        // then
        List<MoveResponse> content = movePage.getContent();
        Assertions.assertThat(content).isNotEmpty().hasSize(1);
        Assertions.assertThat(content).first()
                .extracting(MoveResponse::getName)
                .isEqualTo("move 1");
    }

    @DisplayName("기술 카운터 여부를 (counter) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_카운터_여부D() throws Exception {
        // given
        MoveSearchRequest counterRequest = MoveSearchRequest.builder().counter(true).build();
        MoveSearchRequest nonCounterRequest = MoveSearchRequest.builder().counter(false).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> counterMovePage = moveController.searchAll(counterRequest, pageable);
        Page<MoveResponse> nonCounterMovePage = moveController.searchAll(nonCounterRequest, pageable);

        // then
        List<MoveResponse> counterContent = counterMovePage.getContent();
        Assertions.assertThat(counterContent).isEmpty();

        List<MoveResponse> nonCounterContent = nonCounterMovePage.getContent();
        Assertions.assertThat(nonCounterContent).isNotEmpty().hasSize(5);
        Assertions.assertThat(nonCounterContent)
                .extracting(MoveResponse::isCounter)
                .containsExactly(false, false, false, false, false);
    }

    @DisplayName("발동 프레임을 (startupFrame) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_발동_프레임() throws Exception {
        // given
        MoveSearchRequest startRequest = MoveSearchRequest.builder().startupFrameStart(13).build();
        MoveSearchRequest endRequest = MoveSearchRequest.builder().startupFrameEnd(12).build();
        MoveSearchRequest rangeRequest = MoveSearchRequest.builder()
                .startupFrameStart(11)
                .startupFrameEnd(13)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> startMovePage = moveController.searchAll(startRequest, pageable);
        Page<MoveResponse> endMovePage = moveController.searchAll(endRequest, pageable);
        Page<MoveResponse> rangeMovePage = moveController.searchAll(rangeRequest, pageable);

        // then
        List<MoveResponse> startContent = startMovePage.getContent();
        Assertions.assertThat(startContent).isNotEmpty().hasSize(3);
        Assertions.assertThat(startContent)
                .extracting(MoveResponse::getStartupFrame)
                .containsExactlyInAnyOrder(13, 14, 15);

        List<MoveResponse> endContent = endMovePage.getContent();
        Assertions.assertThat(endContent).isNotEmpty().hasSize(2);
        Assertions.assertThat(endContent)
                .extracting(MoveResponse::getStartupFrame)
                .containsExactlyInAnyOrder(11, 12);

        List<MoveResponse> rangeContent = rangeMovePage.getContent();
        Assertions.assertThat(rangeContent).isNotEmpty().hasSize(3);
        Assertions.assertThat(rangeContent)
                .extracting(MoveResponse::getStartupFrame)
                .containsExactlyInAnyOrder(11, 12, 13);
    }

    @DisplayName("타점 판정을 (hitLevel) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_타점_판정() throws Exception {
        // given
        MoveSearchRequest lowRequest = MoveSearchRequest.builder().hitLevels(List.of(HitLevel.LOW)).build();
        MoveSearchRequest highRequest = MoveSearchRequest.builder().hitLevels(List.of(HitLevel.HIGH)).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> lowMovePage = moveController.searchAll(lowRequest, pageable);
        Page<MoveResponse> highMovePage = moveController.searchAll(highRequest, pageable);

        // then
        List<MoveResponse> lowContent = lowMovePage.getContent();
        Assertions.assertThat(lowContent).isEmpty();

        List<MoveResponse> highContent = highMovePage.getContent();
        Assertions.assertThat(highContent).isNotEmpty().hasSize(5);
        Assertions.assertThat(highContent)
                .extracting(moveResponse -> moveResponse.getHitLevels().getFirst())
                .containsExactly(HitLevel.HIGH, HitLevel.HIGH, HitLevel.HIGH, HitLevel.HIGH, HitLevel.HIGH);
    }

    @DisplayName("가드 프레임을 (guardFrame) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_가드_프레임() throws Exception {
        // given
        MoveSearchRequest startRequest = MoveSearchRequest.builder().guardFrameStart(13).build();
        MoveSearchRequest endRequest = MoveSearchRequest.builder().guardFrameEnd(12).build();
        MoveSearchRequest rangeRequest = MoveSearchRequest.builder()
                .guardFrameStart(11)
                .guardFrameEnd(13)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> startMovePage = moveController.searchAll(startRequest, pageable);
        Page<MoveResponse> endMovePage = moveController.searchAll(endRequest, pageable);
        Page<MoveResponse> rangeMovePage = moveController.searchAll(rangeRequest, pageable);

        // then
        List<MoveResponse> startContent = startMovePage.getContent();
        Assertions.assertThat(startContent).isNotEmpty().hasSize(3);
        Assertions.assertThat(startContent)
                .extracting(MoveResponse::getGuardFrame)
                .containsExactlyInAnyOrder(13, 14, 15);

        List<MoveResponse> endContent = endMovePage.getContent();
        Assertions.assertThat(endContent).isNotEmpty().hasSize(2);
        Assertions.assertThat(endContent)
                .extracting(MoveResponse::getGuardFrame)
                .containsExactlyInAnyOrder(11, 12);

        List<MoveResponse> rangeContent = rangeMovePage.getContent();
        Assertions.assertThat(rangeContent).isNotEmpty().hasSize(3);
        Assertions.assertThat(rangeContent)
                .extracting(MoveResponse::getGuardFrame)
                .containsExactlyInAnyOrder(11, 12, 13);
    }

    @DisplayName("기술 특수효과를 (moveAttributes) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_기술_특수효과() throws Exception {
        // given
        MoveSearchRequest tornadoRequest = MoveSearchRequest.builder()
                .moveAttributes(List.of(MoveAttribute.TORNADO))
                .build();
        MoveSearchRequest powerCrushRequest = MoveSearchRequest.builder()
                .moveAttributes(List.of(MoveAttribute.POWER_CRUSH))
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> tornadoMovePage = moveController.searchAll(tornadoRequest, pageable);
        Page<MoveResponse> powerCrushMovePage = moveController.searchAll(powerCrushRequest, pageable);

        // then
        List<MoveResponse> tornadoContent = tornadoMovePage.getContent();
        Assertions.assertThat(tornadoContent).isEmpty();

        List<MoveResponse> content = powerCrushMovePage.getContent();
        Assertions.assertThat(content).isNotEmpty().hasSize(5);
        Assertions.assertThat(content)
                .extracting(MoveResponse::getMoveAttributes)
                .containsExactly(
                        Set.of(MoveAttribute.POWER_CRUSH),
                        Set.of(MoveAttribute.POWER_CRUSH),
                        Set.of(MoveAttribute.POWER_CRUSH),
                        Set.of(MoveAttribute.POWER_CRUSH),
                        Set.of(MoveAttribute.POWER_CRUSH)
                );
    }

    @DisplayName("기술 분류를 (moveCategory) 이용한 검색을 할 수 있다.")
    @Test
    public void 검색_기술_분류() throws Exception {
        // given
        MoveSearchRequest heatRequest = MoveSearchRequest.builder()
                .moveCategory(MoveCategory.HEAT)
                .build();
        MoveSearchRequest normalRequest = MoveSearchRequest.builder()
                .moveCategory(MoveCategory.NORMAL)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> heatMovePage = moveController.searchAll(heatRequest, pageable);
        Page<MoveResponse> normalMovePage = moveController.searchAll(normalRequest, pageable);

        // then
        List<MoveResponse> heatContent = heatMovePage.getContent();
        Assertions.assertThat(heatContent).isEmpty();

        List<MoveResponse> normalContent = normalMovePage.getContent();
        Assertions.assertThat(normalContent).isNotEmpty().hasSize(5);
        Assertions.assertThat(normalContent)
                .extracting(MoveResponse::getMoveCategory)
                .containsExactly(
                        MoveCategory.NORMAL,
                        MoveCategory.NORMAL,
                        MoveCategory.NORMAL,
                        MoveCategory.NORMAL,
                        MoveCategory.NORMAL
                );
    }
}
