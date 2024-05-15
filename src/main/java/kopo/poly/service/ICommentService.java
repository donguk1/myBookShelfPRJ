package kopo.poly.service;

import kopo.poly.dto.CommentDTO;

import java.util.List;

public interface ICommentService {

    /**
     * 댓글 리스트 가져오기
     */
    List<CommentDTO> getCommentList(final Long boardSeq) throws Exception;

    /**
     * 댓글 작성
     */
    void insertComment(final Long boardSeq,
                      final String userId,
                      final String contents,
                      final int dept,
                      final Long targetSeq) throws Exception;

    /**
     * 댓글 삭제
     */
    int deleteComment(final Long commentSeq,
                      final String userId) throws Exception;

    /**
     * 댓글 수정
     */
    int updateComment(final Long commentSeq,
                      final String userId,
                      final String contents) throws Exception;

    /**
     * 대댓글 작성
     */


}
