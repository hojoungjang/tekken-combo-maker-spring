package com.github.hojoungjang.tekken_combo_maker.character.controller;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/character")
@RequiredArgsConstructor
public class CharacterController {

    private final ICharacterService characterService;

    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable("id") Long id) {
        return characterService.findById(id);
    }

    @GetMapping("/")
    public Page<CharacterDto> getAll(Pageable pageable) {
        return characterService.findAll(pageable);
    }
}
