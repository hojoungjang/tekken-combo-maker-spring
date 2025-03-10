package com.github.hojoungjang.tekken_combo_maker.combo.repository;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.combo.service.IComboRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ComboRepository implements IComboRepository {

    private final ComboJpaRepository comboJpaRepository;

    @Override
    public Page<Combo> findAllByCharacter(Long characterId, Pageable pageable) {
        return comboJpaRepository.findAllByCharacter(characterId, pageable);
    }

    @Override
    public Page<Combo> findAllByPost(Long postId, Pageable pageable) {
        return comboJpaRepository.findAllByPost(postId, pageable);
    }

    @Override
    public List<Combo> saveAll(List<Combo> combos) {
        return comboJpaRepository.saveAll(combos);
    }
}
