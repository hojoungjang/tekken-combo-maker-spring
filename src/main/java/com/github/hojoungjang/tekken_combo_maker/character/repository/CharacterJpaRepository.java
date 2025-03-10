package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterJpaRepository extends JpaRepository<Character, Long> {
}
