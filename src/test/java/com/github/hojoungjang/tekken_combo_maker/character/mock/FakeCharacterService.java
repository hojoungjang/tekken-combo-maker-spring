package com.github.hojoungjang.tekken_combo_maker.character.mock;

import com.github.hojoungjang.tekken_combo_maker.character.controller.ICharacterService;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterResponse;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakeCharacterService implements ICharacterService {

    private final ICharacterRepository characterRepository = new FakeCharacterRepository();

    @Override
    public CharacterResponse findById(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> NotFoundException.supplier(
                        String.format("Character not found with ID: %d", id)
                ));

        return CharacterResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .fullName(character.getFullName())
                .label(character.getLabel())
                .description(character.getDescription())
                .avatarImageName(character.getAvatarImageName())
                .build();
    }

    @Override
    public Page<CharacterResponse> findAll(CharacterSearchRequest request, Pageable pageable) {
        Page<Character> characters = characterRepository.findAll(request, pageable);

        return characters.map(character -> CharacterResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .fullName(character.getFullName())
                .label(character.getLabel())
                .description(character.getDescription())
                .avatarImageName(character.getAvatarImageName())
                .build());
    }
}
