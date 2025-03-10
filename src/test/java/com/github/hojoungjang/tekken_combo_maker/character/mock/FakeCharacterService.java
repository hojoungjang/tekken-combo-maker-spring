package com.github.hojoungjang.tekken_combo_maker.character.mock;

import com.github.hojoungjang.tekken_combo_maker.character.controller.ICharacterService;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class FakeCharacterService implements ICharacterService {

    private final ICharacterRepository characterRepository = new FakeCharacterRepository();

    @Override
    public CharacterDto findById(Long id) {
        Character character = characterRepository.findById(id).get();

        CharacterDto dto = new CharacterDto();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setDescription(character.getDescription());
        dto.setAvatarImageUrl(character.getAvatarImageUrl());
        return dto;
    }

    @Override
    public Page<CharacterDto> findAll(Pageable pageable) {
        Page<Character> characters = characterRepository.findAll(pageable);

        return characters.map(character -> {
            CharacterDto dto = new CharacterDto();
            dto.setId(character.getId());
            dto.setName(character.getName());
            dto.setDescription(character.getDescription());
            dto.setAvatarImageUrl(character.getAvatarImageUrl());
            return dto;
        });
    }
}
