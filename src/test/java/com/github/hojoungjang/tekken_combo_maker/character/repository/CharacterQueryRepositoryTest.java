package com.github.hojoungjang.tekken_combo_maker.character.repository;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CharacterQueryRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CharacterQueryRepositoryTest.class);
    @Autowired
    private EntityManager em;

    @Autowired
    private CharacterQueryRepository repo;

    @Test
    public void getCharacterByIdTest() throws Exception {
//        Character c = Character.builder()
//                .name("Kazuya")
//                .build();
//        em.persist(c);

//        Character qCharacter = repo.getCharacterById(1L);
//        log.info("Queried character id: " + qCharacter.getId());
//        log.info("Queried character name: " + qCharacter.getName());
    }

}