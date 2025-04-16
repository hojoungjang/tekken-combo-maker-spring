package com.github.hojoungjang.tekken_combo_maker.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @DataJpaTest 를 이용해 QueryDSL 코드를 테스트하기 위한 Configuration 클래스
 */
@TestConfiguration
public class TestQueryDslConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
