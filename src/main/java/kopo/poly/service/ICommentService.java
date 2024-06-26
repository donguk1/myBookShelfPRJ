package kopo.poly.service;

import kopo.poly.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICommentService {

    /**
     * 댓글 리스트 가져오기
     */
    List<CommentDTO> getCommentList(final Long boardSeq) throws Exception;

    /**
     * 내 댓글 리스트 가져오기
     */
    Page<CommentDTO> getMyComment(Pageable pageable, final String regId) throws Exception;

    /**
     * 댓글 작성
     */
    void insertComment(final Long boardSeq,
                        final String userId,
                        final String contents,
                        final int dept,
                        final Long targetSeq,
                        final String dt) throws Exception;

    /**
     * 댓글 삭제
     */
    void deleteComment(final Long boardSeq,
                       final Long commentSeq,
                       final String userId) throws Exception;

    /**
     * 댓글 수정
     */
    void updateComment(final Long boardSeq,
                      final Long commentSeq,
                      final String userId,
                      final String contents,
                      final String dt) throws Exception;




}
