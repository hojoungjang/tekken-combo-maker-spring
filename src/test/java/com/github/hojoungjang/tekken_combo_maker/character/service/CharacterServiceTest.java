package com.github.hojoungjang.tekken_combo_maker.character.service;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterResponse;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

class CharacterServiceTest {

    private final CharacterService characterService = new CharacterService(new FakeCharacterRepository());

    @DisplayName("ID 를 사용해 캐릭터 엔티티를 가져온다.")
    @Test
    public void 특정_ID_캐릭터_엔티티를_가져올_수_있다() throws Exception {
        // given
        Long id = 1L;

        // when
        CharacterResponse character = characterService.findById(id);

        // then
        Assertions.assertThat(character.getId()).isEqualTo(1);
        Assertions.assertThat(character.getName()).isEqualTo("character 1");
        Assertions.assertThat(character.getFullName()).isEqualTo("character full 1");
        Assertions.assertThat(character.getLabel()).isEqualTo("캐릭터 1");
        Assertions.assertThat(character.getDescription()).isEqualTo("description 1");
        Assertions.assertThat(character.getAvatarImageName()).isEqualTo("character-1.png");
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
                    CharacterResponse characterDto = characterService.findById(id);
                })
                .withMessageContaining("Character not found with ID: 10");
    }

    @DisplayName("여러 캐릭터 엔티티를 리스트로 가져온다.")
    @Test
    public void 여러_캐릭터_엔티티를_가져올_수_있다() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        CharacterSearchRequest request = CharacterSearchRequest.builder().build();

        // when
        Page<CharacterResponse> charactersPage = characterService.findAll(request, pageable);

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

    @DisplayName("검색조건에 부합하는 캐릭터 엔티티를 가져올 수 있다")
    @ParameterizedTest
    @CsvSource({
            "ch, 3, character 1|character 2|character 3",
            "CHARACTER 1, 1, character 1",
            "cHaRactER 2, 1, character 2",
    })
    public void 검색조건에_부합하는_캐릭터_엔티티를_가져올_수_있다(String searchString, int size, String characterNames) throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        CharacterSearchRequest request = CharacterSearchRequest.builder()
                .search(searchString)
                .build();

        // when
        Page<CharacterResponse> charactersPage = characterService.findAll(request, pageable);

        // then
        List<CharacterResponse> characters = charactersPage.getContent();
        Assertions.assertThat(characters)
                .isNotEmpty()
                .hasSize(size);
        Assertions.assertThat(characters)
                .extracting(CharacterResponse::getName)
                .containsAll(List.of(characterNames.split("\\|")));
    }
}
