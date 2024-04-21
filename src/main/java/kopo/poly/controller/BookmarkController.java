package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.BookmarkDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.repository.entity.IBookmarkService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.color.CMMException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/bookmark")
@RestController
public class BookmarkController {

    private final IBookmarkService bookmarkService;

    /**
     * 북마크 수정
     */
    @PostMapping(value = "updateBookmark")
    public MsgDTO updateBookmark(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller 북마크 수정");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));
        boolean type = Boolean.parseBoolean(CmmUtil.nvl(request.getParameter("type")));
        int res = 1;

        log.info("userId : "+ userId);
        log.info("boardSeq : " + boardSeq);
        log.info("type : " + type);

        try {
            bookmarkService.updateBookmark(userId, boardSeq, type);

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            res = 0;

        }

        return MsgDTO.builder()
                .result(res)
                .build();
    }
}
