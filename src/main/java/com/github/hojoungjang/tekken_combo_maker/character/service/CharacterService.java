package com.github.hojoungjang.tekken_combo_maker.character.service;

import com.github.hojoungjang.tekken_combo_maker.character.controller.ICharacterService;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
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
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> NotFoundException.supplier(String.format("Character not found with ID: %d", id)));

        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .fullName(character.getFullName())
                .description(character.getDescription())
                .avatarImageUrl(character.getAvatarImageUrl())
                .build();
    }

    @Override
    public Page<CharacterDto> findAll(Pageable pageable) {
        Page<Character> characters = characterRepository.findAll(pageable);

        return characters.map(character -> {
            return CharacterDto.builder()
                    .id(character.getId())
                    .name(character.getName())
                    .fullName(character.getFullName())
                    .description(character.getDescription())
                    .avatarImageUrl(character.getAvatarImageUrl())
                    .build();
        });
    }
}
