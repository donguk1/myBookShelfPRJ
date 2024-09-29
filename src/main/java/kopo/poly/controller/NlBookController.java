package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.service.INlBookService;
import kopo.poly.util.CmmUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "insertNlBook")
    public String insertNlBook(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller insertNlBook");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String callNo = CmmUtil.nvl(request.getParameter("callNo"));
        String manageName = CmmUtil.nvl(request.getParameter("manageName"));
        String placeInfo = CmmUtil.nvl(request.getParameter("placeInfo"));

        nlBookService.insertNlBook(callNo, regId, title, manageName, placeInfo);

        return "success";
    }

    @ResponseBody
    @PostMapping(value = "deleteNlBook")
    public String deleteNlBook(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller deleteNlBook");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String callNo = CmmUtil.nvl(request.getParameter("callNo"));

        nlBookService.deleteNlBook(callNo, regId);
        return "success";
    }

    @ResponseBody
    @PostMapping(value = "getNlBook")
    public int getNlBook(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller getNlBook");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String callNo = CmmUtil.nvl(request.getParameter("callNo"));

        return nlBookService.getNlBook(callNo, regId, title);
    }
}
