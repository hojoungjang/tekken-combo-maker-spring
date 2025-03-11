package com.github.hojoungjang.tekken_combo_maker.combo.mock;

import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.combo.service.IComboRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class FakeComboService implements IComboService {

    private final IComboRepository comboRepository = new FakeComboRepository();

    @Override
    public Page<ComboDto> findAllByCharacter(Long characterId, Pageable pageable) {
        Page<Combo> characterComboPage = comboRepository.findAllByCharacter(characterId, pageable);
        return characterComboPage.map(ComboDto::fromEntity);
    }

    @Override
    public Page<ComboDto> findAllByPost(Long postId, Pageable pageable) {
        // TODO: implement
        List<ComboDto> data = new ArrayList<>();
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public List<ComboDto> saveAll(List<ComboCreateRequest> combos) {
        // TODO: implement
        return new ArrayList<>();
    }
}
