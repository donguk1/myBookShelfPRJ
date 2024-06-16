package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.CommentDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICommentService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.SafeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "comment")
@RestController
public class CommentController {

    // ICommentService 객체를 주입
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

        log.info("boardSeq : " + boardSeq);

        return commentService.getCommentList(boardSeq);
    }

    /**
     * 댓글 리스트 가져오기
     */
    @PostMapping(value = "getMyComment")
    public Page<CommentDTO> getMyComment(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller getMyComment");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        int page = SafeUtil.safeParseInt(  // 기본값으로 0을 사용
                request.getParameter("page"), 0
        );

        log.info("userId : " + userId);
        log.info("page : " + page);

        return commentService.getMyComment(PageRequest.of(page-2, 10), userId);
    }

    /**
     * 댓글 수정하기
     */
    @PostMapping(value = "updateComment")
    public MsgDTO updateComment(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller updateComment");

        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));
        Long commentSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("commentSeq")));
        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String contents = CmmUtil.nvl(request.getParameter("upComment"));
        String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

        String msg = "수정 되었습니다.";
        int res = 1;

        try {
            commentService.updateComment(boardSeq, commentSeq, userId, contents, dt);

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            msg = "오류로 인해 실패하였습니다. \n다시 시도해 주세요";
            res = 0;

        }

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    /**
     * 댓글 삭제하기
     */
    @PostMapping(value = "deleteComment")
    public MsgDTO deleteComment(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller updateComment");

        Long boardSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("boardSeq")));
        Long commentSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("commentSeq")));
        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        String msg = "삭제 되었습니다.";
        int res = 1;

        try {
            commentService.deleteComment(boardSeq, commentSeq, userId);

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            msg = "오류로 인해 실패하였습니다. \n다시 시도해 주세요";
            res = 0;

        }

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }


}
