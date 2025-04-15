package com.github.hojoungjang.tekken_combo_maker.character.repository.predicate;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.QCharacter;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

public class CharacterSearchPredicate {

    public static BooleanExpression characterNameContains(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }
        return QCharacter.character.name.upper().contains(search.toUpperCase())
                .or(QCharacter.character.fullName.upper().contains(search.toUpperCase()));
    }
}
