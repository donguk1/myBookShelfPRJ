package kopo.poly.service.impl;

import jakarta.transaction.Transactional;
import kopo.poly.repository.BookmarkRepository;
import kopo.poly.repository.entity.BookmarkEntity;
import kopo.poly.service.IBookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookmarkService implements IBookmarkService {

    private final BookmarkRepository bookmarkRepository;

    /**
     * 북마크 추가 및 삭제
     */
    @Transactional
    @Override
    public void updateBookmark(String userId, Long boardSeq, boolean type) throws Exception {

        log.info("service 북마크 수정 실행");

        if (!type) {
            bookmarkRepository.save(BookmarkEntity.builder()
                    .userId(userId)
                    .boardSeq(boardSeq)
                    .build());

        } else {
            Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserIdAndBoardSeq(userId, boardSeq);
            bookmark.ifPresent(bookmarkRepository::delete);
        }
    }

    /**
     * 북마크 여부 가져오기
     */
    @Override
    public int getBookmark(String userId, Long boardSeq) throws Exception {

        log.info("service 북마크 여부 가져오기");

        Optional<BookmarkEntity> entity = bookmarkRepository.findByUserIdAndBoardSeq(userId, boardSeq);

        int res = 0;

        if (entity.isPresent()) {
            res = 1;
        }

        return res;
    }
}
