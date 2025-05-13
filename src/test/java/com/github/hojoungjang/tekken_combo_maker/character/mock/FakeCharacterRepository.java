package com.github.hojoungjang.tekken_combo_maker.character.mock;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.character.service.ICharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeCharacterRepository implements ICharacterRepository {

    private List<Character> characters = new ArrayList<>();

    public FakeCharacterRepository() {
        for (long i=1; i < 4; i++) {
            String name = "character " + i;
            String fullName = "character full " + i;
            String label = "캐릭터 " + i;
            String description = "description " + i;
            Character character = Character.builder()
                    .name(name)
                    .fullName(fullName)
                    .label(label)
                    .description(description)
                    .build();
            ReflectionTestUtils.setField(character, "id", i);
            characters.add(character);
        }
    }

    @Override
    public Optional<Character> findById(Long id) {
        int idx = id.intValue() - 1;
        if (idx < 0 || idx >= characters.size()) {
            return Optional.empty();
        }
        return Optional.of(characters.get(idx));
    }

    @Override
    public Page<Character> findAll(CharacterSearchRequest request, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        String searchString = StringUtils.hasText(request.getSearch()) ? request.getSearch() : "";

        List<Character> filteredCharacters = characters.stream().filter(
                character -> character.getName().toUpperCase().startsWith(searchString.toUpperCase())
        ).toList();
        List<Character> data = new ArrayList<>(filteredCharacters.subList(offset, Math.min(offset + pageSize, filteredCharacters.size())));
        return new PageImpl<>(data, pageable, data.size());
    }
}
