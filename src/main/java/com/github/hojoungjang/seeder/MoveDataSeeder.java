package com.github.hojoungjang.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.repository.MoveMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

import java.io.InputStream;

@Component
@Slf4j
public class MoveDataSeeder {

    private final ObjectMapper seederObjectMapper = new ObjectMapper();
    private final MoveMongoRepository moveMongoRepository;

    public MoveDataSeeder(MoveMongoRepository moveMongoRepository) {
        this.moveMongoRepository = moveMongoRepository;
    }

    public void run() throws Exception {
        // TODO: Fetch all JSON files under /static/json/move/, parse them and insert all move data
        if (moveMongoRepository.count() == 0) {
            InputStream is = MoveDataSeeder.class.getResourceAsStream("/static/json/move/Kazuya.json");
            JsonNode jsonNode = seederObjectMapper.readTree(is);
            JsonNode movesJson = jsonNode.path("moves");

            List<Move> moves = seederObjectMapper.convertValue(movesJson, new TypeReference<List<Move>>() {
            });
            List<Move> savedMoves = moveMongoRepository.saveAll(moves);
            log.info("Successfully inserted Kazuya moves documents");
        } else {
            log.info("Skipped data initialization");
        }
    }
}
