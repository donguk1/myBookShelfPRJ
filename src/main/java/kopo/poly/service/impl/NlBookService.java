package kopo.poly.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.dto.NlBookDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.repository.NlBookRepository;
import kopo.poly.repository.entity.NlBookEntity;
import kopo.poly.service.INlBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NlBookService implements INlBookService {

    private final NlBookRepository nlBookRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public void insertNlBook(String callNo, String regId, String title, String manageName, String placeInfo, String id) throws Exception {

        log.info("service insertNlBook");

        nlBookRepository.save(
                NlBookEntity.builder()
                        .callNo(callNo)
                        .regId(regId)
                        .title(title)
                        .manageName(manageName)
                        .placeInfo(placeInfo)
                        .id(id)
                        .build()
        );
    }

    @Override
    public void deleteNlBook(String callNo, String regId, String id) throws Exception {

        log.info("service deleteNlBook");

        nlBookRepository.delete(
                NlBookEntity.builder()
                        .callNo(callNo)
                        .regId(regId)
                        .id(id)
                        .build()
        );

    }

    @Override
    public int getNlBook(String callNo, String regId, String title, String id) throws Exception {

        log.info("service getNlBook");

        return nlBookRepository.findByCallNoAndRegIdAndTitle(
                callNo, regId, title)
                .isPresent() ? 1 : 0;
    }

    @Override
    public Page<NlBookDTO> getNlBookList(String regId, Pageable pageable, String keyword) throws Exception {

        log.info("service getNlBookList");

        return nlBookRepository.findByRegIdAndTitleContaining(regId, pageable, keyword)
                .map(NlBookDTO::from);
    }
}
