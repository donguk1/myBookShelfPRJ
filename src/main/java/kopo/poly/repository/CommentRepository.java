package kopo.poly.repository;

import kopo.poly.repository.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


    List<CommentEntity> findByBoardSeqOrderByCommentSeq(Long boardSeq) throws Exception;

}
