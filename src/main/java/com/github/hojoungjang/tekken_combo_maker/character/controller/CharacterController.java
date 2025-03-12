package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/combos")
    public List<Long> createAllCombo(@RequestBody ComboCreateAllRequest request) {
        // TODO: Add base response format and change this response format
        return comboService.saveAll(request);
    }
}
