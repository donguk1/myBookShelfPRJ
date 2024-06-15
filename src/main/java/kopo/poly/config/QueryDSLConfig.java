package kopo.poly.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class QueryDSLConfig {

    // EntityManager 객체를 주입
    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {

        // EntityManager를 사용하여 JPAQueryFactory 객체를 생성하여 반환
        return new JPAQueryFactory(entityManager);
    }
}
