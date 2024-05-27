package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<NoticeEntity, Long> {

    NoticeEntity findByRegDtAndChgDt(String regDt, String chgDt);

//    @Query()
    Page<NoticeEntity> findByRegIdOrderByNoticeSeq(String regId, Pageable pageable, String keyword);
    Page<NoticeEntity> findByRegIdLikeAndTitleOrderByNoticeSeq(String regId, Pageable pageable, String title);

    NoticeEntity findByRegIdAndNoticeSeq(String regId, Long noticeSeq);
}
