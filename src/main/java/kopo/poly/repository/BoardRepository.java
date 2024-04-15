package kopo.poly.repository;

import kopo.poly.repository.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    /**
     * 게시글 리스트 가져오기
     */
    Optional<BoardEntity> findAllByOrderByNoticeYnDescBoardSeqDesc();

    BoardEntity findByRegDtAndChgDt(String regDt, String chgDt);

}
