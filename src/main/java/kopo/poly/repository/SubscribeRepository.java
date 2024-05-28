package kopo.poly.repository;

import kopo.poly.repository.entity.SubscribeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//@Repository
public interface SubscribeRepository {

    Page<SubscribeEntity> getMySubscribeList(Pageable pageable, String regId) throws Exception;
    Page<SubscribeEntity> getMySubscriberList(Pageable pageable, String targetId) throws Exception;
}
