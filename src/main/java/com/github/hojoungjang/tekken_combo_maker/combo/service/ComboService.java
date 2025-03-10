package com.github.hojoungjang.tekken_combo_maker.combo.service;

import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboService implements IComboService {

    private final IComboRepository comboRepository;

    @Override
    public Page<ComboDto> findAllByCharacter(Long characterId, Pageable pageable) {
        Page<Combo> combos = comboRepository.findAllByCharacter(characterId, pageable);
        return combos.map(ComboDto::fromEntity);
    }

    @Override
    public Page<ComboDto> findAllByPost(Long postId, Pageable pageable) {
        Page<Combo> combos = comboRepository.findAllByPost(postId, pageable);
        return combos.map(ComboDto::fromEntity);
    }

    @Override
    public List<ComboDto> saveAll(List<Combo> combos) {
        List<Combo> savedCombos = comboRepository.saveAll(combos);
        return savedCombos.stream().map(ComboDto::fromEntity).toList();
    }
}
