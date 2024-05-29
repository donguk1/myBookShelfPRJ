package kopo.poly.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.repository.custom.PreferRepositoryCustom;
import kopo.poly.repository.entity.PreferEntity;
import kopo.poly.repository.entity.QPreferEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PreferRepositoryImpl implements PreferRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PreferEntity> findByRegId(String regId) throws Exception {

        QPreferEntity qpe = QPreferEntity.preferEntity;

         return queryFactory
                .selectFrom(qpe)
                .where(qpe.regId.eq(regId))
                .fetch();
    }
}
