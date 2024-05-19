package kopo.poly.service;

public interface IBookShelfService {

    /**
     * 도서 추가
     */
    void insertBook(final String title, final String userId, final String regDt) throws Exception;
}
