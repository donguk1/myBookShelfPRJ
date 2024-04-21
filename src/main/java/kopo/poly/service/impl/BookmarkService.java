package kopo.poly.service.impl;

import jakarta.transaction.Transactional;
import kopo.poly.dto.BookmarkDTO;
import kopo.poly.repository.BoardRepository;
import kopo.poly.repository.BookmarkRepository;
import kopo.poly.repository.entity.BookmarkEntity;
import kopo.poly.repository.entity.IBookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        if (type) {
            bookmarkRepository.save(BookmarkEntity.builder()
                    .userId(userId)
                    .boardSeq(boardSeq)
                    .build());

        } else {
            bookmarkRepository.delete(BookmarkEntity.builder()
                    .userId(userId)
                    .boardSeq(boardSeq)
                    .build());
        }
    }
}
