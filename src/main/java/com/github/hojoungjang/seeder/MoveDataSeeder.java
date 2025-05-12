package com.github.hojoungjang.seeder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import com.github.hojoungjang.tekken_combo_maker.move.repository.MoveMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        // TODO: Add params to determine force run or maybe drop all documents first and do a push
        moveMongoRepository.deleteAll();

        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathResolver.getResources("/static/json/move/*.json");
        MappingJsonFactory jsonFactory = new MappingJsonFactory();

        for (Resource resource: resources) {
            InputStream is = resource.getInputStream();
            JsonParser parser = jsonFactory.createParser(is);
            List<Move> moves = new ArrayList<>();

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                if ("moves".equals(parser.currentName())) {
                    int idx = 0;
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        if (parser.nextToken() == JsonToken.START_ARRAY) {
                            continue;
                        }

                        Move move = seederObjectMapper.readValue(parser, Move.class);
                        moves.add(move);

                        if (moves.size() == 20) {
                            moveMongoRepository.insert(moves);
                            moves = new ArrayList<>();

                            log.info(String.format(
                                    "Saved %s moves from index %d to %d...",
                                    resource.getFilename(),
                                    idx*20,
                                    (idx+1) * 20
                            ));
                            idx++;
                        }
                    }

                    if (!moves.isEmpty()) {
                        moveMongoRepository.insert(moves);
                        log.info(String.format(
                                "Saved %s moves from index %d to %d...",
                                resource.getFilename(),
                                idx*20,
                                idx*20 + moves.size()
                        ));
                    }
                }
            }

            log.info(String.format("Saved all moves in %s", resource.getFilename()));
        }
    }
}
