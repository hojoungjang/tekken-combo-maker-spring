package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.mock.FakeComboService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.mock.FakeMoveService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    private CharacterController characterController = new CharacterController(
            new FakeCharacterService(),
            new FakeComboService(),
            new FakeMoveService()
    );

    @DisplayName("ID 를 사용해 캐릭터 정보를 CharacterDto 로 가져온다.")
    @Test
    public void 특정_ID_캐릭터_정보를_가져올_수_있다() throws Exception {
        // given
        Long id = 1L;

        // when
        CharacterDto characterDto = characterController.getById(id);

        // then
        Assertions.assertThat(characterDto.getId()).isEqualTo(1L);
        Assertions.assertThat(characterDto.getName()).isEqualTo("character 1");
        Assertions.assertThat(characterDto.getDescription()).isEqualTo("description 1");
        Assertions.assertThat(characterDto.getAvatarImageUrl()).isEqualTo("image url 1");
    }

    @DisplayName("여러 캐릭터 정보를 CharacterDto 리스트로 가져온다.")
    @Test
    public void 여러_캐릭터_정보를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CharacterDto> charactersPage = characterController.getAll(pageable);

        // then
        List<CharacterDto> characters = charactersPage.getContent();
        Assertions.assertThat(characters)
                .isNotEmpty()
                .hasSize(3);
        Assertions.assertThat(characters)
                .extracting(CharacterDto::getId).contains(1L);
        Assertions.assertThat(characters)
                .extracting(CharacterDto::getName).contains("character 1", "character 2", "character 3");
        Assertions.assertThat(characters)
                .extracting(CharacterDto::getAvatarImageUrl).contains("image url 1", "image url 2", "image url 3");
    }

    @DisplayName("Pagination 을 사용하여 여러 캐릭터 정보를 CharacterDto 리스트로 가져온다.")
    @Test
    public void Pagination을_사용하여_여러_캐릭터_정보를_가져올_수_있다() throws Exception {
        // TODO: 작성하기
    }

    @DisplayName("캐릭터 ID 를 사용하여 해당 캐릭터의 콤보를 가져올 수 있다.")
    @Test
    public void 캐릭터_ID_를_사용하여_해당_캐릭터의_콤보를_가져올_수_있다() throws Exception {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ComboDto> characterComboPage = characterController.getAllCombos(id, pageable);

        // then
        List<ComboDto> characterCombos = characterComboPage.getContent();
        Assertions.assertThat(characterCombos).isNotEmpty().hasSize(1);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getId).contains(1L);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getName).contains("combo 1");
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getDamage).contains(50);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getHitCount).contains(6);
    }

    @DisplayName("캐릭터에 대해 콤보를 생성 할 수 있다.")
    @Test
    public void 캐릭터에_대해_콤보를_생성_할_수_있다() throws Exception {
        // given
        Long characterId = 1L;
        List<ComboCreateRequest> comboPayloads = new ArrayList<>();
        for (long id=1; id <= 3; id++) {
            ComboCreateRequest comboPayload = ComboCreateRequest.builder()
                    .characterId(characterId)
                    .name(String.format("new combo %d", id))
                    .damage(30)
                    .hitCount(2)
                    .build();
            comboPayloads.add(comboPayload);
        }
        ComboCreateAllRequest request = ComboCreateAllRequest.builder()
                .combos(comboPayloads)
                .build();

        // when
        characterController.createAllCombo(request);

        // then
        Pageable pageable = PageRequest.of(0, 10);
        Page<ComboDto> characterComboPage = characterController.getAllCombos(characterId, pageable);
        List<ComboDto> characterCombos = characterComboPage.getContent();
        Assertions.assertThat(characterCombos).isNotEmpty().hasSize(4);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getName)
                .contains("new combo 1", "new combo 2", "new combo 3");
    }

    @DisplayName("캐릭터 기술을 가져올 수 있다.")
    @Test
    public void 캐릭터_기술을_가져올_수_있다() {
        // given
        Long characterId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<MoveResponse> movePage = characterController.getAllMoves(characterId, pageable);

        // then
        List<MoveResponse> moves = movePage.getContent();
        Assertions.assertThat(moves).hasSize(5);
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getId)
                .containsExactlyInAnyOrder("1", "2", "3", "4", "5");
        Assertions.assertThat(moves)
                .extracting(MoveResponse::getName)
                .containsExactlyInAnyOrder("move 1", "move 2", "move 3", "move 4", "move 5");
    }
}
