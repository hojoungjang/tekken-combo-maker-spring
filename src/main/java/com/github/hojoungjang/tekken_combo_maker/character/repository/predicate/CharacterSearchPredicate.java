package com.github.hojoungjang.tekken_combo_maker.character.repository.predicate;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.QCharacter;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

public class CharacterSearchPredicate {

    public static BooleanExpression nameStartsWith(String search) {
        // TODO: might need to also match last name; May need to change full name field to last name
        if (!StringUtils.hasText(search)) {
            return null;
        }
        return QCharacter.character.name.startsWithIgnoreCase(search);
    }
}
