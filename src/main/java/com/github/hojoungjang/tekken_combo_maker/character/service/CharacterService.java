package com.github.hojoungjang.tekken_combo_maker.character.service;

import com.github.hojoungjang.tekken_combo_maker.character.controller.ICharacterService;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterService implements ICharacterService {

    private final ICharacterRepository characterRepository;

    @Override
    public CharacterDto findById(Long id) {
        // TODO: use .orElseThrow() and error handling in controller layer
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
