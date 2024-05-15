package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICommentService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "comment")
@RestController
public class CommentController {

    private final ICommentService commentService;

    /**
     * 댓글 닭기
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

            commentService.insertComment(boardSeq, userId, contents, dept, targetSeq);

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


}
