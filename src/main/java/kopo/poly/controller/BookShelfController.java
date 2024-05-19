package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.BookShelfDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IBookShelfService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

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

    @PostMapping(value = "getMyBookList")
    public List<BookShelfDTO> getMyBookList(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller getMyBookList");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String dt = CmmUtil.nvl(request.getParameter("date"));

        log.info("userId : " + userId);
        log.info("dt : " + dt);

        return bookShelfService.getMyBookList(userId, dt);
    }

    @PostMapping(value = "checkMyBook")
    public List<BookShelfDTO> checkMyBook(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller checkMyBook");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        Date doMonth = Date.valueOf(CmmUtil.nvl(request.getParameter("doMonth")));
        Date nextMonth = Date.valueOf(CmmUtil.nvl(request.getParameter("nextMonth")));

        log.info("userId : " + userId);
        log.info("doMonth : " + doMonth);
        log.info("nextMonth : " + nextMonth);

        return bookShelfService.checkMyBook(userId, doMonth, nextMonth);
    }

    /**
     * 내 도서 수정하기
     */
    @PostMapping(value = "updateMyBook")
    public MsgDTO updateMyBook(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller updateMyBook");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String newTitle = CmmUtil.nvl(request.getParameter("newTitle"));
        String oldTitle = CmmUtil.nvl(request.getParameter("oldTitle"));
        String dt = CmmUtil.nvl(request.getParameter("choiceDay"));

        log.info("userId : " + userId);
        log.info("newTitle : " + newTitle);
        log.info("oldTitle : " + oldTitle);
        log.info("dt : " + dt);

        String msg = "수정 되었습니다";
        int res = 1;

        try {
            bookShelfService.deleteMyBook(userId, dt, oldTitle);
            bookShelfService.insertBook(newTitle, userId, dt);

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

    /**
     * 내 도서 삭제하기
     */
    @PostMapping(value = "deleteBookShelf")
    public MsgDTO deleteBookShelf(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller deleteBookShelf");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String dt = CmmUtil.nvl(request.getParameter("choiceDay"));

        log.info("userId : " + userId);
        log.info("title : " + title);
        log.info("dt : " + dt);

        String msg = "삭제 되었습니다";
        int res = 1;

        try {
            bookShelfService.deleteMyBook(userId, dt, title);

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

    /**
     * 내 도서 가져오기(Pageable)
     */
    @PostMapping(value = "getMyBookPage")
    public Page<BookShelfDTO> getMyBookPage(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller getMyBookPage");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String pageStr = request.getParameter("page");
        int page = safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("userId : " + userId);
        log.info("page : " + page);

        return bookShelfService.getMyBookPage(userId, PageRequest.of(page-2, 10));
    }

    public int safeParseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue; // 기본값 반환
        }
    }
}
