package kopo.poly.repository;

import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<NoticeEntity, Long> {

    NoticeEntity findByRegDtAndChgDt(String regDt, String chgDt);

    @Query(value =
            "SELECT " +
                    "N.NOTICE_SEQ, " +
                    "N.REG_ID, " +
                    "N.TITLE, " +
                    "N.CONTENTS, " +
                    "TO_CHAR(N.REG_DT, 'yyyy-MM-dd') AS REG_DT, " +
                    "N.CHG_DT, " +
                    "N.READ_CNT, " +
                    "FROM NOTICE B " +
                    "WHERE N.TITLE LIKE %:keyword% " +
                    "ORDER BY N.NOTICE_SEQ DESC",
            nativeQuery = true)
    Page<NoticeEntity> findByRegIdOrderByNoticeSeq(String regId, Pageable pageable, String keyword);

    NoticeEntity findByRegIdAndNoticeSeq(String regId, Long noticeSeq);
}
