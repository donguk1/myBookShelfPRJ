package kopo.poly.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "user")
public class UserInfoController {

    private final IUserInfoService userInfoService;
    private final IMailService mailService;

    /**
     * 로그인 실행
     */
    @PostMapping(value = "getLogin")
    public int getLogin(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller 로그인 실행");

        int res = 0;

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));

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

        return "{\"exists\": \"" + existsYn + "\"}";

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

        log.info("existsYn : " + existsYn);
        log.info("controller 이메일 중복체크 완료");

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

                return "{\"exists\": \"" + existsYn + "\"," +
                        "\"authNumber\": \"" + authNumber + "\"}";

            } else {
                log.info("메일 발송 실패");

                return "{\"exists\": \"N\"}";


            }



        } else {
            return "{\"exists\": \"" + existsYn + "\"}";

        }

    }

    /**
     * 회원가입
     */
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUser(HttpServletRequest request) throws Exception {

        log.info("controller 회원가입 실행");

        String msg = "";

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String nickname = CmmUtil.nvl(request.getParameter("nickname"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("userId : " + userId);
        log.info("password : " + password);
        log.info("email : " + email);
        log.info("nickname : " + nickname);
        log.info("userName : " + userName);

        int res = userInfoService.insertUserInfo(userId, password, email, nickname, userName);

        log.info("res : " + res);

        if (res == 1) {
            msg = "회원가입에 성공하였습니다.";

        } else if (res == 2) {
            msg = "이미 존재하는 아이디 입니다.";

        } else {
            msg = "회원가입에 실패하였습니다.";

        }

        return MsgDTO.builder()
                .result(res)
                .msg(msg)
                .build();
    }

    /**
     * 아이디 찾기
     */
    @PostMapping(value = "findId")
    public String findId(HttpServletRequest request) throws Exception {

         log.info("controller 아이디 찾기 실행");

         String email = CmmUtil.nvl(request.getParameter("email"));
         String userName = CmmUtil.nvl(request.getParameter("userName"));

         log.info("email : " + email);
         log.info("userName : " + userName);

         String userId = userInfoService.findId(userName, email);

         log.info("userId : " + userId);

         return "{\"userId\": \"" + userId + "\"}";
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping(value = "newUserPassword")
    public MsgDTO newUserPassword(HttpServletRequest request) throws Exception {

        log.info("controller 비번 찾기 실행");

        String msg = "";
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("userId : " + userId);
        log.info("email : " + email);
        log.info("userName : " + userName);

        int res = userInfoService.findPassword(userId, userName, email);

        log.info("res : " + res);

        if (res == 1) {

            String password = EncryptUtil.encHashSHA256(mailService.getTmpPassword());

            String title = "임시 비밀번호 발송 메일";
            String contents = "임시 비밀번호는 " + password + "입니다.";

            res = mailService.doSendMail(email, title, contents);

            if (res == 1) {
                msg = "임시 비밀번호를 메일로 발송하였습니다.\n메일함을 확인해주세요";

                userInfoService.updatePassword(userId, password, userName, email);

            } else {
                msg = "일치하는 계정이 없습니다. \n다시 확인해주세요";

            }

        } else {
            msg = "일치하는 계정이 없습니다. \n다시 확인해주세요";

        }

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

}
