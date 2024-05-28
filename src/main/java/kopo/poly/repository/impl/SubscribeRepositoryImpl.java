package kopo.poly.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.repository.SubscribeRepository;
import kopo.poly.repository.entity.QSubscribeEntity;
import kopo.poly.repository.entity.QUserInfoEntity;
import kopo.poly.repository.entity.SubscribeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class SubscribeRepositoryImpl implements SubscribeRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SubscribeEntity> getMySubscribeList(Pageable pageable, String regId) throws Exception {

        QSubscribeEntity qse = QSubscribeEntity.subscribeEntity;
        QUserInfoEntity que = QUserInfoEntity.userInfoEntity;

        QueryResults<Tuple> results = queryFactory
                .select(qse, que.nickname, que.userId, que.userName, que.password, que.email)
                .from(qse)
                .join(qse.targetUserInfo, que)
                .fetchJoin()
                .where(qse.regId.eq(regId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        List<SubscribeEntity> subscribeEntities = results.getResults().stream()
                .map(tuple -> {
                    SubscribeEntity subscribeEntity = tuple.get(qse);
                    String nickname = tuple.get(que.nickname);


                    return SubscribeEntity.builder()
                            .regId(regId)
                            .targetId(subscribeEntity.getTargetId())
                            .targetNickname(nickname)
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(subscribeEntities, pageable, results.getTotal());
    }
}
