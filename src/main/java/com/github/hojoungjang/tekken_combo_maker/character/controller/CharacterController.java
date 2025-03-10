package com.github.hojoungjang.tekken_combo_maker.character.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/character")
@RequiredArgsConstructor
public class CharacterController {

    private final ICharacterService characterService;

    @GetMapping
    public String hello() {
        return "Hello";
    }
}
