package kopo.poly.repository;

import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.NoticeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<NoticeEntity, Long> {

    NoticeEntity findByRegDtAndChgDt(String regDt, String chgDt);

}
