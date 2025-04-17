package com.github.hojoungjang.tekken_combo_maker.move.repository;

import com.github.hojoungjang.tekken_combo_maker.move.dto.MoveSearchRequest;
import com.github.hojoungjang.tekken_combo_maker.move.model.document.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class MongoMoveQueryRepository implements MoveQueryRepository {

    private final MongoTemplate template;

    @Override
    public Page<Move> findAll(MoveSearchRequest request, Pageable pageable) {

//        Query query = Query.query(
//                where("characterId")
//                            .is(request.getCharacterId())
//                        .and("startupFrame")
//                            .gte(request.getStartupFrameStart())
//                            .lte(request.getStartupFrameEnd())
//        );

        Query query = new Query();

        if (request.getCharacterId() != null) {
            query.addCriteria(Criteria.where("characterId").is(request.getCharacterId()));
        }

        if (request.getStartupFrameStart() != null && request.getStartupFrameEnd() != null) {
            query.addCriteria(Criteria.where("startupFrame")
                    .gte(request.getStartupFrameStart())
                    .lte(request.getStartupFrameEnd()));
        } else if (request.getStartupFrameStart() != null) {
            query.addCriteria(Criteria.where("startupFrame").gte(request.getStartupFrameStart()));
        } else if (request.getStartupFrameEnd() != null) {
            query.addCriteria(Criteria.where("startupFrame").lte(request.getStartupFrameEnd()));
        }

        if (request.getMoveCategory() != null) {
            query.addCriteria(Criteria.where("moveCategory").is(request.getMoveCategory()));
        }

        List<Move> moves = template.query(Move.class)
                .matching(query).all();
        long count = template.count(query, Move.class);
        return new PageImpl(moves, pageable, count);
    }
}
