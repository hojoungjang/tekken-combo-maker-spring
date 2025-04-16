package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
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
    private final ICharacterQueryRepository characterQueryRepository;

    @Override
    public Optional<Character> findById(Long id) {
        return characterJpaRepository.findById(id);
    }

    @Override
    public Page<Character> findAll(CharacterSearchRequest request, Pageable pageable) {
        return characterQueryRepository.findAll(request, pageable);
    }
}
