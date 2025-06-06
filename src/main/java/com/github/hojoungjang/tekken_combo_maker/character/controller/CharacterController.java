package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterResponse;
import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.controller.IComboService;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllResponse;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.move.controller.IMoveService;
import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController implements SwaggerCharacterController {

    private final ICharacterService characterService;
    private final IComboService comboService;
    private final IMoveService moveService;

    @GetMapping("/{id}")
    public CharacterResponse getById(@PathVariable("id") Long id) {
        return characterService.findById(id);
    }

    @GetMapping
    public Page<CharacterResponse> getAll(CharacterSearchRequest request, Pageable pageable) {
        return characterService.findAll(request, pageable);
    }

    @GetMapping("/{id}/combos")
    public Page<ComboDto> getAllCombos(
            @PathVariable("id") Long id,
            Pageable pageable
    ) {
        return comboService.findAllByCharacter(id, pageable);
    }

    @PostMapping("/{id}/combos")
    public ComboCreateAllResponse createAllCombo(
            @PathVariable("id") Long id,
            @RequestBody ComboCreateAllRequest request
    ) {
        return comboService.saveAll(id, request);
    }

    @GetMapping("/{id}/moves")
    public Page<MoveResponse> getAllMoves(
            @PathVariable("id") Long id,
            Pageable pageable
    ) {
        return moveService.findAllByCharacter(id, pageable);
    }
}
