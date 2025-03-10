package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICharacterService {

    public CharacterDto findById(Long id);

    public Page<CharacterDto> findAll(Pageable pageable);
}
