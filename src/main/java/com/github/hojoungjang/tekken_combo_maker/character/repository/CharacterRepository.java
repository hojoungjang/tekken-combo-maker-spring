package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CharacterRepository implements ICharacterRepository {
    private final CharacterJpaRepository characterJpaRepository;

    @Override
    public Optional<Character> findById(Long id) {
        return characterJpaRepository.findById(id);
    }

    @Override
    public Page<Character> findAll(Pageable pageable) {
        return characterJpaRepository.findAll(pageable);
    }
}
