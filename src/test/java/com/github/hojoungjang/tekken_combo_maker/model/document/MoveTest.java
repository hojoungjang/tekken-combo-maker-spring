package com.github.hojoungjang.tekken_combo_maker.model.document;

import com.github.hojoungjang.tekken_combo_maker.model.enums.HitLevel;
import com.github.hojoungjang.tekken_combo_maker.model.enums.MoveAttribute;
import com.github.hojoungjang.tekken_combo_maker.model.enums.MoveCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoveTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @Transactional
    public void createMove() throws Exception {
        Move move = new Move();
        move.setName("Phoenix Smasher");
        move.setCommand("236RP");
        move.setDamage(30);
        move.setHitCount(1);
        move.setCounter(false);
        move.setStartupFrames(new ArrayList<>(Arrays.asList(13)));
        move.setHitLevels(new ArrayList<>(Arrays.asList(HitLevel.MID)));
        move.setHitFrames(new ArrayList<>(Arrays.asList(25)));
        move.setGuardFrames(new ArrayList<>(Arrays.asList(17)));
        move.setMoveAttributes(new HashSet<>());
        move.setMoveCategory(MoveCategory.NORMAL);

        mongoTemplate.insert(move);

        Move savedMove = mongoTemplate.findById(move.getId(), Move.class);
        assertEquals(move.getId(), savedMove.getId());
    }
}