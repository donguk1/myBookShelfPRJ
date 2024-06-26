package kopo.poly.repository.custom;

import kopo.poly.repository.entity.SubscribeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SubscribeRepositoryCustom {

    Page<SubscribeEntity> getMySubscribeList(Pageable pageable, String regId) throws Exception;
    Page<SubscribeEntity> getMySubscriberList(Pageable pageable, String targetId) throws Exception;
    int getSubCheck(String targetId, String regId) throws Exception;

    
}
