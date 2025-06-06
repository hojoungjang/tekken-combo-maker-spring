package com.github.hojoungjang.tekken_combo_maker.combo.mock;

import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllResponse;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.combo.service.IComboRepository;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class FakeComboService implements IComboService {

    private final IComboRepository comboRepository = new FakeComboRepository();
    private final ICharacterRepository characterRepository = new FakeCharacterRepository();

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
    public ComboCreateAllResponse saveAll(Long characterId, ComboCreateAllRequest request) {
        List<ComboCreateRequest> comboPayloads = request.getCombos();
        List<Combo> combos = comboPayloads.stream().map(comboRequest -> {
            Character character = characterRepository.findById(characterId)
                    .orElseThrow(() -> NotFoundException.supplier(
                            String.format("Character not found with ID: %d", characterId)
                    ));
            return Combo.builder()
                    .character(character)
                    .name(comboRequest.getName())
                    .damage(comboRequest.getDamage())
//                    .hitCount(comboRequest.getHitCount())
                    .build();
        }).toList();
        List<Combo> savedCombos = comboRepository.saveAll(combos);
        return ComboCreateAllResponse.builder()
                .comboIds(savedCombos.stream().map(Combo::getId).toList())
                .build();
    }
}
