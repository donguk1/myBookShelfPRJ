package kopo.poly.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "login")
public class LoginController {

    private final IUserInfoService userInfoService;
    private final IMailService mailService;

//    /**
//     * 아이디 찾기 페이지 이동
//     */
//    @GetMapping(value = "findId")
//    public String findId() {
//
//        log.info("controller 아이디 찾기 페이지 이동");
//
//        return "login.html/findId";
//    }
//
//    /**
//     * 패스워드 찾기 페이지 이동
//     */
//    @GetMapping(value = "findPassword")
//    public String findPasswd() {
//
//        log.info("controller 패스워드 찾기 페이지 이동");
//
//        return "login.html/findPassword";
//    }
//
//    /**
//     * 로그인 페이지 이동
//     */
//    @GetMapping(value = "login")
//    public String login() {
//
//        log.info("controller 로그인 페이지 이동");
//
//        return "login.html/login.html";
//    }
//
//    /**
//     * 회원가입 페이지 이동
//     */
//    @GetMapping(value = "signUp")
//    public String signUp() {
//
//        log.info("controller 회원가입 페이지 이동");
//
//        return "login.html/signUp";
//    }

    /**
     * 로그인 실행
     */
    @PostMapping(value = "getLogin")
    public int getLogin(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller 로그인 실행");

        int res = 0;

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(request.getParameter("password"));

        log.info("userId : " + userId);
        log.info("password : " + password);

        try {
            res = userInfoService.getLogin(userId, password);

            if (res == 1) {
                session.setAttribute("SS_USER_ID", userId);
            }

        } catch (Exception e) {
            log.warn(e.toString());
            e.printStackTrace();

        }

        log.info("controller 로그인 종료");

        return res;
    }

    /**
     * 아이디 중복 체크
     */
    @PostMapping(value = "getUserIdExists")
    public String getUserIdExists(HttpServletRequest request) throws Exception {

        log.info("controller 아이디 중복체크 실행");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        String existsYn = userInfoService.getUserIdExists(userId);

        log.info("existsYn : " + existsYn);
        log.info("controller 아이디 중복체크 완료");

        return existsYn;

    }

    /**
     * 이메일 중복 체크
     */
    @PostMapping(value = "getEmailExists")
    public String getEmailExists(HttpServletRequest request) throws Exception {

        log.info("controller 이메일 중복체크 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        String existsYn = userInfoService.getEmailExists(email);


        if (existsYn.equals("Y")) {

            log.info("인증번호 생성 시작");

            // 6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 10000000);

            log.info("authNumber : " + authNumber);

            String title = "이메일 인증번호 발송 메일";
            String contents = "인증번호는 " + authNumber + "입니다.";

            int res = mailService.doSendMail(email, title, contents);

            if (res == 1) { // 발송 성공

                log.info("메일 발송");

            } else {
                log.info("메일 발송 실패");

            }
        }

        log.info("existsYn : " + existsYn);
        log.info("controller 이메일 중복체크 완료");

        return existsYn;

    }
}
