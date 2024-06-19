package kopo.poly.repository;

import kopo.poly.repository.entity.CommentEntity;
import kopo.poly.repository.entity.PK.CommentPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, CommentPK> {


    List<CommentEntity> findByBoardSeqOrderByCommentSeq(Long boardSeq) throws Exception;

    CommentEntity findByRegDt(String regDt) throws Exception;

    CommentEntity findTopByBoardSeq(Long boardSeq) throws Exception;

    Long countByBoardSeq(Long boardSeq) throws Exception;

    CommentEntity findTopByBoardSeqOrderByCommentSeqDesc(Long boardSeq) throws Exception;

    Page<CommentEntity> findByRegId(Pageable pageable, String regId) throws Exception;

}
