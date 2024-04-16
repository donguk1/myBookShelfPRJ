package kopo.poly.repository;

import kopo.poly.repository.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    /**
     * 게시글 리스트 가져오기
     */
    Optional<BoardEntity> findAllByOrderByNoticeYnDescBoardSeqDesc();

    /**
     * 작성된 PK 가져오기
     */
    BoardEntity findByRegDtAndChgDt(String regDt, String chgDt);

    /**
     * 리스트 가져오기
     */
    @Query(value =
            "SELECT " +
                    "B.BOARD_SEQ," +
                    "B.REG_ID, " +
                    "B.NOTICE_YN," +
                    "B.TITLE," +
                    "B.CATEGORY," +
                    "B.CONTENTS," +
                    "U.NICKNAME," +
                    "TO_CHAR(B.REG_DT, 'yyyy-MM-dd') AS REG_DT, " +
                    "B.CHG_DT," +
                    "B.READ_CNT," +
                    "(SELECT COUNT(C.COMMENT_SEQ)" +
                        "FROM COMMENT C " +
                        "WHERE C.BOARD_SEQ = B.BOARD_SEQ) AS COMMENT_CNT, " +
                    "CASE WHEN EXISTS (" +
                        "SELECT 1 FROM FILE F WHERE B.BOARD_SEQ = F.BOARD_SEQ" +
                        ") THEN 'Y' ELSE 'N' END AS FILE_YN " +
            "FROM BOARD B " +
                    "LEFT JOIN USER_INFO U ON B.REG_ID = U.USER_ID " +
            "ORDER BY B.NOTICE_YN DESC, B.BOARD_SEQ DESC",
    nativeQuery = true)
    Optional<List<BoardEntity>> getBoardList();

}
