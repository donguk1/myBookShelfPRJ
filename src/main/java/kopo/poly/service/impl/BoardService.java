package kopo.poly.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import kopo.poly.dto.BoardDTO;
import kopo.poly.repository.BoardRepository;
import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.QBoardEntity;
import kopo.poly.repository.entity.QUserInfoEntity;
import kopo.poly.service.IBoardService;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 리스트 가져오기
     */
    @Override
    public List<BoardDTO> getBoardList() throws Exception {

        log.info("service 보드 리스트 가져오기");

        List<BoardEntity> pList = boardRepository.getBoardList()
                .orElse(Collections.emptyList());

        List<BoardDTO> bList = new ArrayList<>();

        pList.forEach(e -> {
            BoardDTO rDTO = BoardDTO.builder()
                    .boardSeq(e.getBoardSeq())
                    .regId(e.getRegId())
                    .noticeYn(e.getNoticeYn())
                    .title(e.getTitle())
                    .category(e.getCategory())
                    .contents(e.getContents())
                    .commentCnt(e.getCommentCnt())
                    .chgDt(e.getChgDt())
                    .regDt(e.getRegDt())
                    .readCnt(e.getReadCnt())
                    .nickname(e.getNickname())
                    .fileYn(e.getFileYn())
                    .build();

            bList.add(rDTO);
        });

        return bList;
    }

    /**
     * 게시글 상세정보 가져오기
     * @param boardSeq PK
     * @param type 조회수 증가여부
     */
    @Override
    @Transactional
    public BoardDTO getBoardInfo(Long boardSeq, Boolean type) throws Exception {

        log.info("service 상세정보 가져오기");

        if (type) {
            boardRepository.updateReadCnt(boardSeq);

        }

        QBoardEntity be = QBoardEntity.boardEntity;
        QUserInfoEntity ue = QUserInfoEntity.userInfoEntity;

        BoardEntity bEntity = queryFactory
                .selectFrom(be)
                .join(be.userInfo, ue)
                .where(be.boardSeq.eq(boardSeq))
                .fetchOne();

        return BoardDTO.builder()
                .boardSeq(bEntity.getBoardSeq())
                .title(bEntity.getTitle())
                .category(bEntity.getCategory())
                .readCnt(bEntity.getReadCnt())
                .nickname(bEntity.getUserInfo().getNickname())
                .regDt(bEntity.getRegDt())
                .chgDt(bEntity.getChgDt())
                .contents(bEntity.getContents())
                .regId(bEntity.getRegId())
                .noticeYn(bEntity.getNoticeYn())
                .build();
    }

    /**
     * 게시글 업데이트
     */
    @Transactional
    @Override
    public int updateBoard(Long boardSeq, String userId, String title, String noticeYn, String category, String contents) throws Exception {

        log.info("service 게시글 업데이트");

        String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

        boardRepository.save(BoardEntity.builder()
                .boardSeq(boardSeq)
                .title(title)
                .noticeYn(noticeYn)
                .category(category)
                .contents(contents)
                .readCnt(0L)
                .chgDt(dt)
                .build());

        return 1;
    }

    /**
     * 게시글 작성하기
     */
    @Override
    public Long insertBoard(String regId, String title, String noticeYn, String category, String contents) throws Exception {

        log.info("service 게시글 작성하기");

        String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

        boardRepository.save(BoardEntity.builder()
                .regId(regId)
                .title(title)
                .noticeYn(noticeYn)
                .category(category)
                .contents(contents)
                .readCnt(0L)
                .regDt(dt)
                .chgDt(dt)
                .build());

        BoardEntity bEntity = boardRepository.findByRegDtAndChgDt(dt, dt);

        return bEntity.getBoardSeq();
    }

    /**
     * 게시글 삭제하기
     */
    @Override
    @Transactional
    public void deleteBoard(Long boardSeq) throws Exception {

        boardRepository.delete(BoardEntity.builder()
                        .boardSeq(boardSeq)
                        .build());
    }
}
