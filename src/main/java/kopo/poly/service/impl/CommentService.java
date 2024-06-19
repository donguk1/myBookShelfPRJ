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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {

    private final JPAQueryFactory queryFactory;

    private final CommentRepository commentRepository;

    private List<CommentEntity> getChildren(List<CommentEntity> parents, Long boardSeq) {

        List<CommentEntity> result = new ArrayList<>();

        QUserInfoEntity ue = QUserInfoEntity.userInfoEntity;
        QCommentEntity ce = QCommentEntity.commentEntity;

        // 댓글 목록(dept == 0) 반복
        for (CommentEntity parent : parents) {

            // 댓글을 결과에 추가
            result.add(parent);

            // 대댓글 조회
            List<CommentEntity> children = queryFactory
                    .selectFrom(ce)
                    // 조인 작성자 nickname 가져오기 위한 조인
                    .join(ce.userInfo, ue).fetchJoin()
                    .where(ce.boardSeq.eq(boardSeq),
                            ce.targetSeq.eq(parent.getCommentSeq()))
                    .fetch();

            // dept가 2이상이 있을경우 사용하는 재귀함수
            result.addAll(getChildren(children, boardSeq));
        }

        return result;
    }

    /**
     * 댓글 리스트 가져오기
     */
    @Override
    public List<CommentDTO> getCommentList(Long boardSeq) throws Exception {

        log.info("service 댓글 리스트 가져오기");

        QUserInfoEntity ue = QUserInfoEntity.userInfoEntity;
        QCommentEntity ce = QCommentEntity.commentEntity;

        // 최상위 댓글(dept == 0) 목록 조회
        List<CommentEntity> cList = queryFactory
                .selectFrom(ce)
                .join(ce.userInfo, ue).fetchJoin()
                .where(ce.boardSeq.eq(boardSeq),
                        ce.targetSeq.eq(0L))
                .fetch();

        // 계층 구조의 댓글 목록 가져옴
        List<CommentEntity> hierarchicalResult  = getChildren(cList, boardSeq);

        List<CommentDTO> dtoList = new ArrayList<>();

        // 엔터티를 DTO로 생성자를 사용한 변환
        hierarchicalResult .forEach(commentEntity ->
            dtoList.add(CommentDTO.from(commentEntity))
        );

        return dtoList;
    }

    /**
     * 내 댓글 리스트 가져오기
     */
    @Override
    public Page<CommentDTO> getMyComment(Pageable pageable, String regId) throws Exception {

        log.info("service getMyComment");

        // Pageable을 사용하여 댓글을 페이지 단위로 조회
        return commentRepository.findByRegId(pageable, regId)
                .map(CommentDTO::myComment);
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

        // 현재 게시글의 댓글수 조회(댓글 없을시 0으로 초기화)
        Long commentSeq = Optional.ofNullable(
                commentRepository.findTopByBoardSeqOrderByCommentSeqDesc(boardSeq))
                .map(CommentEntity::getCommentSeq)
                .orElse(0L);

        log.info(String.valueOf(commentSeq));

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
    @Transactional
    @Override
    public void deleteComment(Long boardSeq, Long commentSeq, String userId) throws Exception {

        log.info("service deleteComment");

        commentRepository.delete(CommentEntity.builder()
                        .boardSeq(boardSeq)
                        .commentSeq(commentSeq)
                        .regId(userId)
                        .build());

        commentRepository.deleteAll(commentRepository.findByBoardSeqAndTargetSeq(boardSeq, commentSeq));
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
