package kopo.poly.service.impl;

import kopo.poly.repository.BookShelfRepository;
import kopo.poly.repository.entity.BookShelfEntity;
import kopo.poly.service.IBookShelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookShelfService implements IBookShelfService {

    private final BookShelfRepository bookShelfRepository;

    /**
     * 도서 추가
     */
    @Override
    public void insertBook(String title, String userId, String regDt) throws Exception {

        log.info("service insertBook");

        bookShelfRepository.save(BookShelfEntity.builder()
                        .title(title)
                        .regId(userId)
                        .regDt(regDt)
                        .build());
    }
}
