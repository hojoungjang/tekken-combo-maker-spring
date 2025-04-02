package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICharacterService {

    public CharacterResponse findById(Long id);

    public Page<CharacterResponse> findAll(Pageable pageable);
}
