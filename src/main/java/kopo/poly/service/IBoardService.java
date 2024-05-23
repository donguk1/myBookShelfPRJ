package kopo.poly.service;

import kopo.poly.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBoardService {

    /**
     * 리스트 가져오기
     */
    List<BoardDTO> getBoardList() throws Exception;

    Page<BoardDTO> getBoardList(Pageable pageable,
                                final String category,
                                final String keyword) throws Exception;

    /**
     * 게시글 상세정보 가져오기
     * @param boardSeq PK
     * @param type 조회수 증가여부
     */
    BoardDTO getBoardInfo(final Long boardSeq,
                          final Boolean type) throws Exception;

    /**
     * 게시글 업데이트
     */
    int updateBoard(final Long boardSeq,
                    final String userId,
                    final String title,
                    final String noticeYn,
                    final String category,
                    final String contents) throws Exception;

    /**
     * 게시글 작성하기
     */
    Long insertBoard(final String regId,
                     final String title,
                     final String noticeYn,
                     final String category,
                     final String contents) throws Exception;

    /**
     * 게시글 삭제하기
     */
    void deleteBoard(final Long boardSeq)throws Exception;

    /**
     * 내 북마크 가져오기
     */
    Page<BoardDTO> getMyBookmarkList(Pageable pageable,
                                     final String userId,
                                     final String keyword,
                                     final String category) throws Exception;

    /**
     * 내 게시글 가져오기
     */
    Page<BoardDTO> getMyBoard(Pageable pageable,
                              final String userId,
                              final String keyword,
                              final String category) throws Exception;
}
