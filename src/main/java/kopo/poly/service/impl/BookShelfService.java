package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.dto.BookShelfDTO;
import kopo.poly.repository.BookShelfRepository;
import kopo.poly.repository.entity.BookShelfEntity;
import kopo.poly.repository.entity.QBookShelfEntity;
import kopo.poly.service.IBookShelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookShelfService implements IBookShelfService {

    private final BookShelfRepository bookShelfRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 도서 추가
     */
    @Override
    public void insertBook(String title, String userId, String regDt) throws Exception {

        log.info("service insertBook");

        bookShelfRepository.save(BookShelfEntity.builder()
                        .title(title)
                        .regId(userId)
                        .regDt(Date.valueOf(regDt))
                        .build());
    }

    /**
     * 도서 가져오기
     */
    @Override
    public List<BookShelfDTO> getMyBookList(String userId, String dt) throws Exception {

        log.info("service getMyBookList");

        QBookShelfEntity bse = QBookShelfEntity.bookShelfEntity;

        List<BookShelfEntity> bsList = queryFactory
                .selectFrom(bse)
                .where(bse.regId.eq(userId),
                        bse.regDt.eq(Date.valueOf(dt)))
                .fetch();

        return bsList.stream()
                .map(bsd -> BookShelfDTO.builder()
                        .title(bsd.getTitle())
                        .regId(bsd.getRegId())
                        .regDt(bsd.getRegDt().toLocalDate())
                        .build())
                .collect(Collectors.toList());
    };

    /**
     * 도서 가져오기(Pageable)
     */
    @Override
    public Page<BookShelfDTO> getMyBookPage(String userId, Pageable pageable) throws Exception {

        log.info("service getMyBookPage");

        return bookShelfRepository.findByRegIdOrderByRegDt(userId, pageable).map(BookShelfDTO::from);
    }

    /**
     * 도서 여부
     */
    @Override
    public List<BookShelfDTO> checkMyBook(String regId, Date doMonth, Date nextMonth) throws Exception {

        log.info("service checkMyBook");

        QBookShelfEntity bse = QBookShelfEntity.bookShelfEntity;

        List<BookShelfEntity> bsList = queryFactory
                .selectFrom(bse)
                .where(bse.regId.eq(regId),
                        bse.regDt.between(doMonth, nextMonth))
                .fetch();

        return bsList.stream()
                .map(bsd -> BookShelfDTO.builder()
                        .title(bsd.getTitle())
                        .regId(bsd.getRegId())
                        .regDt(bsd.getRegDt().toLocalDate())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 도서 삭제하기
     */
    @Override
    public void deleteMyBook(String regId, String dt, String title) throws Exception {

        log.info("service deleteMyBook");

        bookShelfRepository.delete(BookShelfEntity.builder()
                        .regId(regId)
                        .regDt(Date.valueOf(dt))
                        .title(title)
                        .build());
    }
}
