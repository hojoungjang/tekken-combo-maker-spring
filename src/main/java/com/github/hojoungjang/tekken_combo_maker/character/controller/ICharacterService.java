package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;

import java.util.List;

public interface ICharacterService {

    public CharacterDto findById(Long id);

    public List<CharacterDto> findAll();
}
