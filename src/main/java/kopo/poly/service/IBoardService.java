package kopo.poly.service;

import kopo.poly.dto.BoardDTO;

import java.util.List;

public interface IBoardService {

    /**
     * 리스트 가져오기
     */
    List<BoardDTO> getBoardList() throws Exception;

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
}
