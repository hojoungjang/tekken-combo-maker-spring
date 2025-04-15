package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICharacterQueryRepository {
    Page<Character> findAll(CharacterSearchRequest request, Pageable pageable);
}
