package kopo.poly.service.impl;


import kopo.poly.dto.BoardDTO;
import kopo.poly.repository.BoardRepository;
import kopo.poly.service.IBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        return null;
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
    @Override
    public int updateBoard(Long boardSeq, String userId, String title, String noticeYn, String category, String contents) throws Exception {
        return 0;
    }


}
