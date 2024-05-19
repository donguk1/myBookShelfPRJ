package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IBookShelfService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CacheMode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.dgc.VMID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "bookShelf")
@RestController
public class BookShelfController {

    private final IBookShelfService bookShelfService;

    @PostMapping(value = "insertBook")
    public MsgDTO insertBook(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller insertBook");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String dt = CmmUtil.nvl(request.getParameter("choiceDay"));

        log.info("userId : " + userId);
        log.info("title : " + title);
        log.info("dt : " + dt);

        String msg = "추가 되었습니다";
        int res = 1;

        try {
            bookShelfService.insertBook(title, userId, dt);

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            msg = "오류로 인해 실패 했습니다. \n다시 실행해 주세요";
            res = 1;
        }

        return MsgDTO.builder()
                .result(res)
                .msg(msg)
                .build();
    }
}
