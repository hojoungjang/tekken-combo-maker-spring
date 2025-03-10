package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    private CharacterController characterController = new CharacterController(new FakeCharacterService());

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
}
