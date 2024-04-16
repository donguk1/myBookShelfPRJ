package kopo.poly.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.BoardDTO;
import kopo.poly.repository.BoardRepository;
import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.service.IBoardService;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;

    /**
     * 리스트 가져오기
     */
    @Override
    public List<BoardDTO> getBoardList() throws Exception {

        log.info("service 보드 리스트 가져오기");

        List<BoardEntity> pList = boardRepository.getBoardList()
                .orElse(Collections.emptyList());

        return new ObjectMapper().convertValue(pList,
                new TypeReference<List<BoardDTO>>() {
                });
    }

    /**
     * 게시글 상세정보 가져오기
     * @param boardSeq PK
     * @param type 조회수 증가여부
     */
    @Override
    public BoardDTO getBoardInfo(Long boardSeq, Boolean type) throws Exception {
        return null;
    }

    /**
     * 게시글 업데이트
     */
    @Transactional
    @Override
    public int updateBoard(Long boardSeq, String userId, String title, String noticeYn, String category, String contents) throws Exception {
        return 0;
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
}
