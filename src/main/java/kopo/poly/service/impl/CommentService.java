package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.CommentDTO;
import kopo.poly.repository.CommentRepository;
import kopo.poly.repository.entity.CommentEntity;
import kopo.poly.repository.entity.QCommentEntity;
import kopo.poly.repository.entity.QUserInfoEntity;
import kopo.poly.service.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {

    private final JPAQueryFactory queryFactory;

    private final CommentRepository commentRepository;

    /**
     * 댓글 리스트 가져오기
     */
    @Override
    public List<CommentDTO> getCommentList(Long boardSeq) throws Exception {

        log.info("service 댓글 리스트 가져오기");

        QUserInfoEntity ue = QUserInfoEntity.userInfoEntity;
        QCommentEntity ce = QCommentEntity.commentEntity;

        List<CommentEntity> cList = queryFactory
                .selectFrom(ce)
                .join(ce.userInfo, ue)
                .where(ce.boardSeq.eq(boardSeq))
                .fetchJoin().fetch();
        
        List<CommentDTO> dtoList = new ArrayList<>();
        
        cList.forEach(commentEntity -> {

            dtoList.add(CommentDTO.builder()
                        .boardSeq(commentEntity.getBoardSeq())
                        .commentSeq(commentEntity.getCommentSeq())
                        .dept(commentEntity.getDept())
                        .targetSeq(commentEntity.getTargetSeq())
                        .regId(commentEntity.getRegId())
                        .nickname(commentEntity.getUserInfo().getNickname())
                        .regDt(commentEntity.getRegDt())
                        .chgDt(commentEntity.getChgDt())
                        .contents(commentEntity.getContents())
                        .build());
        });

        return dtoList;
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
