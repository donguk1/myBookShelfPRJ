package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.FileDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IFileService;
import kopo.poly.service.INoticeService;
import kopo.poly.service.IS3Service;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.FileUtil;
import kopo.poly.util.SafeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "notice")
@Controller
public class NoticeController {

    private final INoticeService noticeService;

    private final IS3Service s3Service;

    private final IFileService fileService;

    @GetMapping(value = "noticeList")
    public String noticeList() {
        return "notice/noticeList";
    }

    @GetMapping(value = "noticeReg")
    public String noticeReg() {
        return "notice/noticeReg";
    }

    @GetMapping(value = "noticeInfo")
    public String noticeInfo() {
        return "notice/noticeInfo";
    }

    @GetMapping(value = "noticeEditInfo")
    public String noticeEditInfo() {
        return "notice/noticeEditInfo";
    }


    @ResponseBody
    @PostMapping(value = "insertNotice")
    public MsgDTO insertNotice(HttpServletRequest request, HttpSession session,
                               @RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {

        log.info("controller insertNotice");

        int res = 1;
        String msg = "";

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

        log.info("ss_user_id : " + userId);
        log.info("title : " + title);
        log.info("contents : " + contents);


        try {
            Long noticeSeq = noticeService.insertNotice(userId, title, contents);

            log.info("noticeSeq : " + noticeSeq);

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
                                .noticeSeq(noticeSeq)
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

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            res = 0;
            msg = "오류로 인해 실패하였습니다. 다시 실행해주세요";

        }

        log.info("qwe");

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    @ResponseBody
    @PostMapping(value = "getNoticeList")
    public Page<NoticeDTO> getNoticeList(HttpServletRequest request) throws Exception {

        log.info("controller getNoticeList ");

        String keyword = CmmUtil.nvl(request.getParameter("keyword"));
        String pageStr = request.getParameter("page");
        int page = SafeUtil.safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("keyword : " + keyword);
        log.info("page : " + page);

        return noticeService.getNoticeList(
                PageRequest.of(page-2, 10), keyword);

    }

    @ResponseBody
    @PostMapping(value = "getNoticeInfo")
    public NoticeDTO getNoticeInfo(HttpServletRequest request) throws Exception {

        log.info("controller 게시글 가져오기");

        Long nSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("nSeq")));
        Boolean type = Boolean.valueOf(CmmUtil.nvl(request.getParameter("type")));

        return noticeService.getNoticeInfo(nSeq, type);
    }

    @ResponseBody
    @PostMapping(value = "updateNotice")
    public MsgDTO updateNotice(HttpServletRequest request, HttpSession session,
                               @RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {

        log.info("controller updateNotice");

        int res = 1;
        String msg = "";

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
        Long nSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("nSeq")));
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

        log.info("ss_user_id : " + userId);
        log.info("nSeq : " + nSeq);
        log.info("title : " + title);
        log.info("contents : " + contents);


        try {
            res = noticeService.updateNotice(nSeq, title, contents);

            log.info("nSeq : " + nSeq);

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
                                .noticeSeq(nSeq)
                                .orgFileName(orgFileName)
                                .saveFilePath(saveFilePath)
                                .fileSize(fileSize)
                                .saveFileName(rDTO.saveFileName())
                                .saveFileUrl(rDTO.saveFileUrl())
                                .build();

                        log.info("sageFileUrl : " + rDTO.saveFileUrl());

                        fileService.deleteFiles(nSeq);

                        fileService.saveFiles(fileDTO);

                        fileDTO = null;

                    }
                }
            }
            msg = "작성되었습니다.";

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            res = 0;
            msg = "오류로 인해 실패하였습니다. 다시 실행해주세요";

        }


        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    @ResponseBody
    @PostMapping(value = "deleteNotice")
    public MsgDTO deleteNotice(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller deleteNotice");

        Long nSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("nSeq")));
        String msg;
        int res = 1;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("userId : " + userId);
            log.info("nSeq : " + nSeq);

            noticeService.deleteNotice(nSeq);

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


}
