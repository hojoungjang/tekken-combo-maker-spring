package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.github.hojoungjang.tekken_combo_maker.character.model.entity.QCharacter.character;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CharacterQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Character getCharacterById(Long id) {
        Character c = jpaQueryFactory.query()
                .select(character)
                .from(character)
                .where(character.id.eq(id))
                .fetchOne();
        return c;
    }
}
