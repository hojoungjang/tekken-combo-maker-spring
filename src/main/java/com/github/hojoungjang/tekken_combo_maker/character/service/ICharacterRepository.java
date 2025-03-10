package com.github.hojoungjang.tekken_combo_maker.character.service;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICharacterRepository {

    public Optional<Character> findById(Long id);

    public Page<Character> findAll(Pageable pageable);
}
