package kopo.poly.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.dto.SubscribeDTO;
import kopo.poly.repository.SubscribeRepository;
import kopo.poly.service.ISubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscribeService implements ISubscribeService {

    private final SubscribeRepository subscribeRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SubscribeDTO> getMySubscribeList(Pageable pageable, String regId) throws Exception {

        log.info("service getMySubscribeList");

        return subscribeRepository.getMySubscribeList(pageable, regId)
                .map(SubscribeDTO::from);
    }

    @Override
    public Page<SubscribeDTO> getMySubscriberList(Pageable pageable, String targetId) throws Exception {

        log.info("service getMySubscriberList");

        return subscribeRepository.getMySubscriberList(pageable, targetId)
                .map(SubscribeDTO::from);
    }

    @Override
    public int getSubCheck(String targetId, String regId) throws Exception {

        log.info("service getSubCheck");

        return subscribeRepository.getSubCheck(targetId, regId);
    }
}
