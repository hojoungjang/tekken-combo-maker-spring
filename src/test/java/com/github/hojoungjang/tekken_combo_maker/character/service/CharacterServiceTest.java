package com.github.hojoungjang.tekken_combo_maker.character.service;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterServiceTest {

    private CharacterService characterService = new CharacterService(new FakeCharacterRepository());

    @Test
    public void testFindById() throws Exception {
        // given
        Long id = 1L;

        // when
        CharacterDto character = characterService.findById(id);

        // then
        Assertions.assertThat(character.getId()).isEqualTo(1);
        Assertions.assertThat(character.getName()).isEqualTo("character 1");
        Assertions.assertThat(character.getDescription()).isEqualTo("description 1");
        Assertions.assertThat(character.getAvatarImageUrl()).isEqualTo("image url 1");
    }

    @Test
    public void testFindAll() throws Exception {
        // given
        // when
        List<CharacterDto> characters = characterService.findAll();

        // then
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
}