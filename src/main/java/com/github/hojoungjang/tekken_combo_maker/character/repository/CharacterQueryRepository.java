package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.hojoungjang.tekken_combo_maker.character.model.entity.QCharacter.character;
import static com.github.hojoungjang.tekken_combo_maker.character.repository.predicate.CharacterSearchPredicate.nameStartsWith;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CharacterQueryRepository implements ICharacterQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Character> findAll(CharacterSearchRequest request, Pageable pageable) {
        String search = request.getSearch();
        long offset = pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Character> characters = jpaQueryFactory
                .select(character)
                .from(character)
                .where(nameStartsWith(search))
                .offset(offset)
                .limit(pageSize)
                .fetch();

        Long countResult = jpaQueryFactory
                .select(character.count())
                .from(character)
                .where(nameStartsWith(search))
                .fetchOne();

        long total = 0;
        if (countResult != null) {
            total = countResult;
        }
        return new PageImpl<>(characters, pageable, total);
    }
}
