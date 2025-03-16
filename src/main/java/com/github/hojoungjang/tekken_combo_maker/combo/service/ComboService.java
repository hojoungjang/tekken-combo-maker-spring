package com.github.hojoungjang.tekken_combo_maker.combo.service;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComboService implements IComboService {

    private final IComboRepository comboRepository;
    private final ICharacterRepository characterRepository;

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
    public List<Long> saveAll(ComboCreateAllRequest request) {
        List<ComboCreateRequest> comboPayloads = request.getCombos();
        List<Combo> combos = comboPayloads.stream().map(comboRequest -> {
            Long characterId = comboRequest.getCharacterId();
            // TODO: optimize DB query
            Character character = characterRepository.findById(characterId)
                    .orElseThrow(() -> NotFoundException.supplier(
                            String.format("Character not found with ID: %d", characterId)
                    ));
            return Combo.builder()
                    .character(character)
                    .name(comboRequest.getName())
                    .damage(comboRequest.getDamage())
                    .hitCount(comboRequest.getHitCount())
                    .build();
        }).toList();
        List<Combo> savedCombos = comboRepository.saveAll(combos);
        return savedCombos.stream().map(Combo::getId).toList();
    }
}
