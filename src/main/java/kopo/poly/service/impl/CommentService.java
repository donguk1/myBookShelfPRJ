package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.CommentDTO;
import kopo.poly.repository.CommentRepository;
import kopo.poly.repository.entity.CommentEntity;
import kopo.poly.service.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 리스트 가져오기
     */
    @Override
    public List<CommentDTO> getCommentList(Long boardSeq) throws Exception {

        log.info("service 댓글 리스트 가져오기");

        return new ObjectMapper().convertValue(
                commentRepository.findByBoardSeqOrderByCommentSeq(boardSeq),
                new TypeReference<>() {
                });
    }

    /**
     * 댓글 작성
     */
    @Override
    public void insertComment(Long boardSeq,
                              String userId,
                              String contents,
                              int dept,
                              Long targetSeq,
                              String dt) throws Exception {

        log.info("service insertComment");

        CommentEntity pEntity = commentRepository.findTopByBoardSeq(boardSeq);

        if (pEntity == null) {
            pEntity = CommentEntity.builder()
                    .commentSeq(0L)
                    .build();
        }

        CommentEntity entity = CommentEntity.builder()
                .boardSeq(boardSeq)
                .commentSeq(pEntity.getCommentSeq() + 1)
                .regId(userId)
                .contents(contents)
                .dept(dept)
                .targetSeq(targetSeq)
                .regDt(dt)
                .chgDt(dt)
                .build();

        commentRepository.save(entity);
    }

    /**
     * 댓글 삭제
     */
    @Override
    public int deleteComment(Long commentSeq, String userId) throws Exception {
        return 0;
    }

    /**
     * 댓글 수정
     */
    @Override
    public int updateComment(Long commentSeq, String userId, String contents) throws Exception {
        return 0;
    }
}
