package kopo.poly.repository.entity;

public interface IBookmarkService {

    /**
     * 북마크 추가 및 삭제
     */
    void updateBookmark(final String userId,
                        final Long boardSeq,
                        final boolean type) throws Exception;
}
