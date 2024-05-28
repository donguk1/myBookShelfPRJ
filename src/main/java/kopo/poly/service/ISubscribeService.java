package kopo.poly.service;

import kopo.poly.dto.SubscribeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubscribeService {

    Page<SubscribeDTO> getMySubscribeList(Pageable pageable, String regId) throws Exception;
    Page<SubscribeDTO> getMySubscriberList(Pageable pageable, String targetId) throws Exception;
}
