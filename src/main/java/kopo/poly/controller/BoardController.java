package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.FileDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IBoardService;
import kopo.poly.service.IFileService;
import kopo.poly.service.IS3Service;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "board")
public class BoardController {

    private final IBoardService boardService;
    private final IS3Service s3Service;
    private final IFileService fileService;

    /**
     * 게시글 작성하기
     */
    @PostMapping(value = "insertBoard")
    public MsgDTO insertBoard(HttpSession session, HttpServletRequest request,
                              @RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {

        log.info("controller 게시글 작성");

        int res = 1;
        String msg = "";

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
        String category = CmmUtil.nvl(request.getParameter("category")); // 카테고리
        String noticeYn = "N"; // 공지글 여부

        log.info("ss_user_id : " + userId);
        log.info("title : " + title);
        log.info("noticeYn : " + noticeYn);
        log.info("contents : " + contents);
        log.info("category : " + category);

        try {
            Long boardSeq = boardService.insertBoard(userId, title, noticeYn, category, contents);

            log.info("boardSeq : " + boardSeq);

            if (files != null) {

                String saveFilePath = FileUtil.mkdirForData();      // 웹서버에 저장할 파일 경로 생성

                for (MultipartFile mf : files) {

                    log.info("mf : " + mf);

                    String orgFileName = mf.getOriginalFilename();      // 파일의 원본 명
                    String fileSize = String.valueOf(mf.getSize());     // 파일 크기
                    String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1,    // 확장자
                            orgFileName.length()).toLowerCase();

                    // 이미지 파일만 실행되도록 함
                    if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

                        log.info("orgFileName : " + orgFileName);
                        log.info("fileSize : " + fileSize);
                        log.info("ext : " + ext);
                        log.info("saveFilePath : " + saveFilePath);

                        FileDTO rDTO = s3Service.uploadFile(mf, ext);

                        FileDTO fileDTO = FileDTO.builder()
                                .boardSeq(boardSeq)
                                .orgFileName(orgFileName)
                                .saveFilePath(saveFilePath)
                                .fileSize(fileSize)
                                .saveFileName(rDTO.saveFileName())
                                .saveFileUrl(rDTO.saveFileUrl())
                                .build();

                        log.info("sageFileUrl : " + rDTO.saveFileUrl());

                        fileService.saveFiles(fileDTO);

                        fileDTO = null;

                    }
                }
            }
            msg = "작성되었습니다.";
            log.info("123");

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            res = 0;
            msg = "오류로 인해 실패하였습니다. 다시 실행해주세요";
            log.info("345");

        }

        log.info("qwe");

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();

    }


    /**
     * 게시글 리스트 가져오기
     */
    @GetMapping(value = "getBoardList")
    public Map<String, Object> getBoardList(@RequestParam(defaultValue = "1") int page) throws Exception {

        log.info("controller 게시글 리스트 가져오기");

        List<BoardDTO> bList = boardService.getBoardList();

        log.info("page : " + page);

        // 페이지당 보여줄 아이템 개수 정의
        int itemPerPage = 10;

        // 페이지네이션을 위해 전체 아이템 개수 구하기
        int totalItems = bList.size();

        // 전체 페이지 개수 계산
        int totalPages = (int) Math.ceil((double) totalItems / itemPerPage);

        // 현재 페이지에 해당하는 아이템들만 선택하여 rList에 할당
        int fromIndex = (page - 1) * itemPerPage;
        int toIndex = Math.min(fromIndex + itemPerPage, totalItems);
        bList = bList.subList(fromIndex, toIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", page);
        map.put("totalPages", totalPages);
        map.put("bList", bList);

        return map;
    }
}
