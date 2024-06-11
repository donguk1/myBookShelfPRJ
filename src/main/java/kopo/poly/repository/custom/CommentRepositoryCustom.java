package kopo.poly.repository.custom;

import kopo.poly.repository.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> findByBoardSeqOrderByCommentSeq(Long boardSeq) throws Exception;

    CommentEntity findByRegDt(String regDt) throws Exception;

    CommentEntity findTopByBoardSeq(Long boardSeq) throws Exception;

    Long countByBoardSeq(Long boardSeq) throws Exception;

    Page<CommentEntity> findByRegId(Pageable pageable, String regId) throws Exception;

}
