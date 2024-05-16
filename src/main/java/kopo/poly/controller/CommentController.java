package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.CommentDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICommentService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "comment")
@RestController
public class CommentController {

    private final ICommentService commentService;

    /**
     * 댓글 달기
     */
    @PostMapping(value = "insertComment")
    public MsgDTO insertComment(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller insertComment");

        String msg = "작성되었습니다.";
        int res = 1;

        try {
            Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));
            int dept = Integer.parseInt(CmmUtil.nvl(request.getParameter("dept")));
            Long targetSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("targetSeq")));
            String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

            log.info("boardSeq : " + boardSeq);
            log.info("userId : " + userId);
            log.info("contents : " + contents);
            log.info("dept : " + dept);
            log.info("targetSeq : " + targetSeq);
            log.info("dt : " + dt);

            commentService.insertComment(boardSeq, userId, contents, dept, targetSeq, dt);

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            msg = "오류로 인해 실패했습니다.\n다시 실행 해주세요";
            res = 0;
        }

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    /**
     * 댓글 리스트 가져오기
     */
    @PostMapping(value = "getCommentList")
    public List<CommentDTO> getCommentList(HttpServletRequest request) throws Exception {

        log.info("controller getCommentList");

        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));

        return commentService.getCommentList(boardSeq);
    }
}
