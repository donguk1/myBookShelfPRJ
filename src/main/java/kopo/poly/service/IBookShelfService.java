package kopo.poly.service;

import kopo.poly.dto.BookShelfDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface IBookShelfService {

    /**
     * 도서 추가
     */
    void insertBook(final String title, final String userId, final String regDt) throws Exception;

    /**
     * 도서 가져오기
     */
    List<BookShelfDTO> getMyBookList(final String userId, final String dt) throws Exception;

    /**
     * 도서 여부
     */
    List<BookShelfDTO> checkMyBook(final String regId, final Date doMonth, final Date nextMonth) throws Exception;

    /**
     * 도서 삭제하기
     */
    void deleteMyBook(final String regId, final String dt, final String title) throws Exception;
}
