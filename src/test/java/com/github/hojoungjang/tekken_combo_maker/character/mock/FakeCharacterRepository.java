package com.github.hojoungjang.tekken_combo_maker.character.mock;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeCharacterRepository implements ICharacterRepository {

    private List<Character> characters = new ArrayList<>();

    public FakeCharacterRepository() {
        for (long i=1; i < 4; i++) {
            String name = "character " + i;
            String description = "description " + i;
            String avatarImageUrl = "image url " + i;
            Character character = new Character(i, name, description, avatarImageUrl);
            characters.add(character);
        }
    }

    @Override
    public Optional<Character> findById(Long id) {
        int idx = id.intValue() - 1;
        return Optional.of(characters.get(idx));
    }

    @Override
    public Page<Character> findAll(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Character> data = new ArrayList<>(characters.subList(offset, Math.min(offset + pageSize, characters.size())));
        return new PageImpl<>(data, pageable, data.size());
    }
}
