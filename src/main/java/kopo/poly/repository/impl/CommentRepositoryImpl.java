package kopo.poly.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.repository.custom.CommentRepositoryCustom;
import kopo.poly.repository.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<CommentEntity> findByBoardSeqOrderByCommentSeq(Long boardSeq) throws Exception {
        return null;
    }

    @Override
    public CommentEntity findByRegDt(String regDt) throws Exception {
        return null;
    }

    @Override
    public CommentEntity findTopByBoardSeq(Long boardSeq) throws Exception {
        return null;
    }

    @Override
    public Long countByBoardSeq(Long boardSeq) throws Exception {
        return null;
    }

    @Override
    public Page<CommentEntity> findByRegId(Pageable pageable, String regId) throws Exception {
        return null;
    }
}
