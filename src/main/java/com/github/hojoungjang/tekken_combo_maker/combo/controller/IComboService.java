package com.github.hojoungjang.tekken_combo_maker.combo.controller;

import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IComboService {

    Page<ComboDto> findAllByCharacter(Long characterId, Pageable pageable);
    Page<ComboDto> findAllByPost(Long postId, Pageable pageable);
    List<Long> saveAll(ComboCreateAllRequest request);
}
