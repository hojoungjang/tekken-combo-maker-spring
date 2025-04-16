package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.dto.CharacterSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.common.config.TestQueryDslConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest
@Import(TestQueryDslConfig.class)
class CharacterQueryRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CharacterQueryRepositoryTest.class);

    @TestConfiguration
    static class TestConfig {
        @Bean
        CharacterQueryRepository repo(JPAQueryFactory jpaQueryFactory) {
            return new CharacterQueryRepository(jpaQueryFactory);
        };
    }

    @Autowired
    private CharacterJpaRepository jpaRepo;

    @Autowired
    private CharacterQueryRepository queryRepo;

    @BeforeEach
    void setUp() {
        jpaRepo.deleteAll();
        Character kazuya = Character.builder()
                .name("Kazuya")
                .fullName("Kazuya Mishima")
                .description("This is for unit test")
                .build();

        Character heihachi = Character.builder()
                .name("Heihachi")
                .fullName("Heihachi Mishima")
                .description("This is for unit test")
                .build();
        jpaRepo.save(kazuya);
        jpaRepo.save(heihachi);
    }

    @DisplayName("검색 조건을 이용하여 캐릭터 검색을 할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "kaz, 1, Kazuya",
            "KAZUYA, 1, Kazuya",
            "hEIHA, 1, Heihachi",
            "h, 1, Heihachi"
    })
    public void findAllTest(String searchString, int size, String characterName) throws Exception {
        // given
        CharacterSearchRequest request = CharacterSearchRequest.builder().search(searchString).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Character> charactersPage = queryRepo.findAll(request, pageable);

        // then
        List<Character> characters = charactersPage.getContent();
        Assertions.assertThat(characters).isNotEmpty().hasSize(size);
        Assertions.assertThat(characters)
                .extracting(Character::getName).containsExactly(characterName);
    }

}