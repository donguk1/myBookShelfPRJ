package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.service.INlBookService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "nlBook")
@Controller
public class NlBookController {

    private final INlBookService nlBookService;



    @GetMapping(value = "nlBookList")
    public String nlBookList() {
        return "nlBook/nlBookList";
    }

    @ResponseBody
    @PostMapping(value = "checkNlBook")
    public int checkNlBook(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller checkNlBook");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String callNo = CmmUtil.nvl(request.getParameter("callNo"));
        String title = CmmUtil.nvl(request.getParameter("titleText"));
        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("regId : {}", regId);
        log.info("title : {}", title);
        log.info("callNo : {}", callNo);
        log.info("id : {}", id);

        int res = nlBookService.getNlBook(callNo, regId, title, id);

        log.info("res : {}", res);

        if (res == 0) {
            String manageName = CmmUtil.nvl(request.getParameter("manageName"));
            String placeInfo = CmmUtil.nvl(request.getParameter("placeInfo"));

            log.info("manageName : {}", manageName);
            log.info("placeInfo : {}", placeInfo);

            nlBookService.insertNlBook(
                    callNo, regId, title, manageName, placeInfo, id
            );
        } else {
            nlBookService.deleteNlBook(callNo, regId, id);
        }
        return res;
    }


    @ResponseBody
    @PostMapping(value = "getNlBook")
    public int getNlBook(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller getNlBook");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String title = CmmUtil.nvl(request.getParameter("titleText"));
        String callNo = CmmUtil.nvl(request.getParameter("callNo"));
        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("regId : {}", regId);
        log.info("title : {}", title);
        log.info("callNo : {}", callNo);
        log.info("id : {}", id);

        return nlBookService.getNlBook(callNo, regId, title, id);
    }
}
