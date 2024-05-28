package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.SubscribeDTO;
import kopo.poly.service.ISubscribeService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.SafeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "subscribe")
@RestController
public class SubscribeController {

    private final ISubscribeService subscribeService;

    /**
     * 내 구독 리스트 보기
     */
    @PostMapping(value = "getMySubscribeList")
    public Page<SubscribeDTO> getRecordBoardList(HttpServletRequest request, HttpSession session) throws Exception {

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String pageStr = request.getParameter("page");
        int page = SafeUtil.safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("page : " + page);
        log.info("regId : " + regId);

        return subscribeService.getMySubscribeList(
                PageRequest.of(page-2, 10), regId
        );
    }

    /**
     * 내 구독자 리스트 보기
     */
    @PostMapping(value = "getMySubscriberList")
    public Page<SubscribeDTO> getMySubscriberList(HttpServletRequest request, HttpSession session) throws Exception {

        String targetId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String pageStr = request.getParameter("page");
        int page = SafeUtil.safeParseInt(pageStr, 0); // 기본값으로 0을 사용

        log.info("page : " + page);
        log.info("targetId : " + targetId);

        return subscribeService.getMySubscriberList(
                PageRequest.of(page-2, 10), targetId
        );
    }

    /**
     * 구독여부 확인하기
     */
    @PostMapping(value = "getSubCheck")
    public int getSubCheck(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller getSubCheck");

        String targetId = CmmUtil.nvl(request.getParameter("targetId"));
        String regId = CmmUtil.nvl(request.getParameter("regId"));

        return subscribeService.getSubCheck(targetId, regId);
    }
}
