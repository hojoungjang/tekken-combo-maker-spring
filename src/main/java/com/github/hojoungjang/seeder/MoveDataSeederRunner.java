package com.github.hojoungjang.seeder;

import com.github.hojoungjang.tekken_combo_maker.move.repository.MoveMongoRepository;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MoveDataSeederRunner {

    public static void main(String[] args) throws Exception{

        // TODO: find ways to insert args and determine modes such as create/update

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                MongoDataAutoConfiguration.class,
                MoveDataSeederConfig.class
        );

        MoveMongoRepository moveMongoRepo = context.getBean(MoveMongoRepository.class);

        MoveDataSeeder moveDataSeeder = new MoveDataSeeder(moveMongoRepo);
        moveDataSeeder.run();
        context.close();
    }
}
