package kopo.poly.service;

public interface IBookmarkService {

    /**
     * 북마크 추가 및 삭제
     */
    void updateBookmark(final String userId,
                        final Long boardSeq,
                        final boolean type) throws Exception;

    /**
     * 북마크 여부 가져오기
     */
    int getBookmark(final String userId,
                    final Long boardSeq) throws Exception;
}
