package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterResponse;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.mock.FakeComboService;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import com.github.hojoungjang.tekken_combo_maker.move.mock.FakeMoveService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

class CharacterControllerTest {

    private final CharacterController characterController = new CharacterController(
            new FakeCharacterService(),
            new FakeComboService(),
            new FakeMoveService()
    );

    @DisplayName("ID 를 사용해 캐릭터 정보를 CharacterResponse 로 가져온다.")
    @Test
    public void 특정_ID_캐릭터_정보를_가져올_수_있다() throws Exception {
        // given
        Long id = 1L;

        // when
        CharacterResponse response = characterController.getById(id);

        // then
        Assertions.assertThat(response.getId()).isEqualTo(1L);
        Assertions.assertThat(response.getName()).isEqualTo("character 1");
        Assertions.assertThat(response.getFullName()).isEqualTo("character full 1");
        Assertions.assertThat(response.getLabel()).isEqualTo("캐릭터 1");
        Assertions.assertThat(response.getDescription()).isEqualTo("description 1");
        Assertions.assertThat(response.getAvatarImageName()).isEqualTo("character-1.png");
    }

    @DisplayName("캐릭터 ID 가 존재하지 않으면 NotFoundException 예외를 던지다.")
    @Test
    public void 캐릭터_ID_가_존재하지_않는다() throws Exception {
        // given
        Long id = 10L;

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    CharacterResponse characterDto = characterController.getById(id);
                })
                .withMessageContaining("Character not found with ID: 10");
    }

    @DisplayName("여러 캐릭터 정보를 CharacterResponse 리스트로 가져온다.")
    @Test
    public void 여러_캐릭터_정보를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        CharacterSearchRequest request = CharacterSearchRequest.builder().build();

        // when
        Page<CharacterResponse> charactersPage = characterController.getAll(request, pageable);

        // then
        List<CharacterResponse> characters = charactersPage.getContent();
        Assertions.assertThat(characters)
                .isNotEmpty()
                .hasSize(3);
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getId).contains(1L);
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getName)
                .contains("character 1", "character 2", "character 3");
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getFullName)
                .contains("character full 1", "character full 2", "character full 3");
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getLabel)
                .contains("캐릭터 1", "캐릭터 2", "캐릭터 3");
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getAvatarImageName)
                .contains("character-1.png", "character-2.png", "character-3.png");
    }

    @DisplayName("Pagination 을 사용하여 여러 캐릭터 정보를 CharacterResponse 리스트로 가져온다.")
    @Test
    public void Pagination을_사용하여_여러_캐릭터_정보를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(1, 1);
        CharacterSearchRequest request = CharacterSearchRequest.builder().build();

        // when
        Page<CharacterResponse> charactersPage = characterController.getAll(request, pageable);

        // then
        List<CharacterResponse> characters = charactersPage.getContent();
        Assertions.assertThat(characters).isNotEmpty().hasSize(1);

        CharacterResponse character = characters.getFirst();
        Assertions.assertThat(character.getId()).isEqualTo(2L);
        Assertions.assertThat(character.getName()).isEqualTo("character 2");
        Assertions.assertThat(character.getFullName()).isEqualTo("character full 2");
        Assertions.assertThat(character.getLabel()).isEqualTo("캐릭터 2");
        Assertions.assertThat(character.getAvatarImageName()).isEqualTo("character-2.png");
    }

    @DisplayName("검색조건에 부합하는 여러 캐릭터 정보를 가져올 수 있다")
    @ParameterizedTest
    @CsvSource({
            "ch, 3, character 1|character 2|character 3",
            "CHARACTER 1, 1, character 1",
            "cHaRactER 2, 1, character 2",
    })
    public void 검색조건에_부합하는_여러_캐릭터_정보를_가져올_수_있다(String searchString, int size, String characterNames) throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        CharacterSearchRequest request = CharacterSearchRequest.builder()
                .search(searchString)
                .build();

        // when
        Page<CharacterResponse> charactersPage = characterController.getAll(request, pageable);

        // then
        List<CharacterResponse> characters = charactersPage.getContent();
        Assertions.assertThat(characters)
                .isNotEmpty()
                .hasSize(size);
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getName)
                .containsAll(List.of(characterNames.split("\\|")));
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

    @DisplayName("존재하지 않는 캐릭터에 대해 콤보를 생성 할 수 없다.")
    @Test
    public void 존재하지_않는_캐릭터에_대해_콤보를_생성_할_수_없다() throws Exception {
        // given
        Long characterId = 10L;
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
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                        characterController.createAllCombo(request);
                })
                .withMessageContaining("Character not found with ID: 10");
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
    }
}
