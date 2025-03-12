package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final ICharacterService characterService;
    private final IComboService comboService;

    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable("id") Long id) {
        return characterService.findById(id);
    }

    @GetMapping
    public Page<CharacterDto> getAll(Pageable pageable) {
        return characterService.findAll(pageable);
    }

    @GetMapping("/{id}/combos")
    public Page<ComboDto> getAllCombos(
            @PathVariable("id") Long id,
            Pageable pageable
    ) {
        return comboService.findAllByCharacter(id, pageable);
    }
}
