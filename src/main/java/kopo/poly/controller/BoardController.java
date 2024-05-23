package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.FileDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IBoardService;
import kopo.poly.service.ICommentService;
import kopo.poly.service.IFileService;
import kopo.poly.service.IS3Service;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/board")
public class BoardController {

    private final IBoardService boardService;
    private final ICommentService commentService;
    private final IS3Service s3Service;
    private final IFileService fileService;
    /**
     * 보드 리스트 이동
     */
    @GetMapping(value = "/boardList")
    public String boardList() throws Exception {

        return "board/boardList";
    }

    /**
     * 작성페이지 이동
     */
    @GetMapping(value = "/boardReg")
    public String boardReg() {
        return "board/boardReg";
    }

    /**
     * 상세보기 이동
     */
    @GetMapping(value = "/boardInfo")
    public String boardInfo() {return "/board/boardInfo";}

    /**
     * 수정 이동
     */
    @GetMapping(value = "/boardEditInfo")
    public String boardEditInfo() {return "/board/boardEditInfo";}

    /**
     * 게시글 작성하기
     */
    @ResponseBody
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
     * 게시글 리스트 가져오기(사용 X)
     */
    @ResponseBody
    @GetMapping(value = "getBoardList")
    public Map<String, Object> getBoardList(@RequestParam(defaultValue = "1") int page) throws Exception {

        log.info("controller 게시글 리스트 가져오기");

        List<BoardDTO> bList = boardService.getBoardList();

        log.info("page : " + page);

        // 페이지당 보여줄 아이템 개수 정의
        int itemPerPage = 0;

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

    /**
     * 게시글 리스트 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getBoardListPage")
    public Page<BoardDTO> getBoardListPage(HttpServletRequest request) throws Exception {

        log.info("controller getBoardListPage 게시글 리스트 가져오기");

        String pageStr = request.getParameter("page");
        int page = safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("page : " + page);

        return boardService.getBoardList(
                PageRequest.of(page-2, 10));
    }

    /**
     * 게시글 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getBoardInfo")
    public BoardDTO getBoardInfo(HttpServletRequest request) throws Exception {

        log.info("controller 게시글 가져오기");

        Long bSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("bSeq")));
        Boolean type = Boolean.valueOf(CmmUtil.nvl(request.getParameter("type")));

        return boardService.getBoardInfo(bSeq, type);
    }

    /**
     * 게시글 수정하기
     */
    @ResponseBody
    @PostMapping(value = "updateBoard")
    public MsgDTO updateBoard(HttpSession session, HttpServletRequest request,
                              @RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {

        log.info("controller 게시글 업데이트");

        int res = 1;
        String msg = "";

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("bSeq"))); // 제목
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
        String category = CmmUtil.nvl(request.getParameter("category")); // 카테고리
        String noticeYn = "N"; // 공지글 여부

        log.info("ss_user_id : " + userId);
        log.info("boardSeq : " + boardSeq);
        log.info("title : " + title);
        log.info("noticeYn : " + noticeYn);
        log.info("contents : " + contents);
        log.info("category : " + category);

        try {
            res = boardService.updateBoard(boardSeq, userId, title, noticeYn, category, contents);


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

                        fileService.deleteFiles(boardSeq);

                        fileService.saveFiles(fileDTO);

                        fileDTO = null;

                    }
                }
            }
            msg = "수정되었습니다.";
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
     * 게시글 삭제
     */
    @ResponseBody
    @PostMapping(value = "deleteBoard")
    public MsgDTO deleteBoard(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller 게시글 삭제");

        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));
        String msg;
        int res = 1;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("userId : " + userId);
            log.info("boardSeq : " + boardSeq);

            boardService.deleteBoard(boardSeq);

            msg = "삭제되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다.";
            res = 0;

            log.info(e.toString());
            e.printStackTrace();

        }

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    /**
     * 내 북마크 리스트 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getMyBookmark")
    public Page<BoardDTO> getMyBookmark(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller 내 북마크 리스트 가져오기");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String pageStr = request.getParameter("page");
        int page = safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("userId : " + userId);
        log.info("page : " + page);

        return boardService.getMyBookmarkList(PageRequest.of(page-2, 10), userId);
    }

    /**
     * 내 게시글 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getMyBoard")
    public Page<BoardDTO> getMyBoard(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller getMyBoard");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String pageStr = request.getParameter("page");
        int page = safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("userId : " + userId);
        log.info("page : " + page);

        return boardService.getMyBoard(PageRequest.of(page-2, 10), userId);

    }

    public int safeParseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue; // 기본값 반환
        }
    }
}
