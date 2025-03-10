package com.github.hojoungjang.tekken_combo_maker.combo.service;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IComboRepository {

    public Page<Combo> findAllByCharacter(Long characterId, Pageable pageable);

    public Page<Combo> findAllByPost(Long postId, Pageable pageable);

    public List<Combo> saveAll(List<Combo> combos);
}
