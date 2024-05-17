package kopo.poly.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kopo.poly.dto.CommentDTO;
import kopo.poly.repository.CommentRepository;
import kopo.poly.repository.entity.CommentEntity;
import kopo.poly.repository.entity.QCommentEntity;
import kopo.poly.repository.entity.QUserInfoEntity;
import kopo.poly.service.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Long commentSeq = commentRepository.countByBoardSeq(boardSeq);

        if (commentSeq == null) {
            commentSeq = 0L;
        }

        CommentEntity entity = CommentEntity.builder()
                .boardSeq(boardSeq)
                .commentSeq(commentSeq + 1)
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
    public void deleteComment(Long commentSeq, String userId) throws Exception {


    }

    /**
     * 댓글 수정
     */
    @Transactional
    @Override
    public void updateComment(Long boardSeq, Long commentSeq, String userId, String contents, String dt) throws Exception {

        log.info("service updateComment");

        CommentEntity entity = CommentEntity.builder()
                .boardSeq(boardSeq)
                .commentSeq(commentSeq)
                .regId(userId)
                .contents(contents)
                .chgDt(dt)
                .build();

        commentRepository.save(entity);
    }
}