package kopo.poly.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
public class UserInfoController {

    private final IUserInfoService userInfoService;
    private final IMailService mailService;

    /**
     * 아이디 찾기 이동
     */
    @GetMapping(value = "/findId")
    public String findId() {
        return "user/findId";
    }
    
    /**
     * 비밀번호 찾기 이동
     */
    @GetMapping(value = "/findPassword")
    public String findPassword() {
        return "user/findPassword";
    }

    /**
     * 로그인 이동
     */
    @GetMapping(value = "/login")
    public String login() {
        return "user/login";
    }

    /**
     * 내 활동 이동
     */
    @GetMapping(value = "/myActivity")
    public String myActivity() {
        return "user/myActivity";
    }

    /**
     * 내 정보 이동
     */
    @GetMapping(value = "/myInfo")
    public String myInfo() {
        return "user/myInfo";
    }
    
    /**
     * 마이페이지 이동
     */
    @GetMapping(value = "/myPage")
    public String myPage() {
        return "user/myPage";
    }

    /**
     * 내 정보 이동
     */
    @GetMapping(value = "/myBoard")
    public String myBoard() {
        return "user/myBoard";
    }

    /**
     * 회원가입 이동
     */
    @GetMapping(value = "/signUp")
    public String signUp() {
        return "user/signUp";
    }

    /**
     * 내 북마크 리스트
     */
    @GetMapping(value = "/myBookmark")
    public String myBookmark() { return "user/myBookmark"; };

    /**
     * 내 댓글 리스트
     */
    @GetMapping(value = "/myComment")
    public String myComment() { return "user/myComment"; };

    /**
     * 내 책장 보기
     */
    @GetMapping(value = "/bookShelf")
    public String bookShelf() { return "user/bookShelf"; };

    /**
     * 캘린더 보기
     */
    @GetMapping(value = "/calendar")
    public String calendar() { return "user/calendar"; };

    /**
     * 내 도서 보기
     */
    @GetMapping(value = "/myBookList")
    public String myBookList() { return "user/myBookList"; };

    /**
     * 내 구독 보기
     */
    @GetMapping(value = "/mySubscriber")
    public String mySubscriber() { return "user/mySubscriber"; };

    /**
     * 내 구독자 보기
     */
    @GetMapping(value = "/mySubscribe")
    public String mySubscribe() { return "user/mySubscribe"; };


    /**
     * 로그인 실행
     */
    @ResponseBody
    @PostMapping(value = "loginProc")
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

                log.info("123");
                session.setAttribute("SS_USER_ID", userId);
//                session.setMaxInactiveInterval(3600);
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    @PostMapping(value = "getUserId")
    public String getUserId(HttpServletRequest request) throws Exception {

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
    @ResponseBody
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

            String password = mailService.getTmpPassword();

            String title = "임시 비밀번호 발송 메일";
            String contents = "임시 비밀번호는 " + password + "입니다.";

            res = mailService.doSendMail(email, title, contents);

            if (res == 1) {
                msg = "임시 비밀번호를 메일로 발송하였습니다.\n메일함을 확인해주세요";

                userInfoService.updatePassword(userId, EncryptUtil.encHashSHA256(password), userName, email);

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

    /**
     * 내 정보 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getUserInfo")
    public UserInfoDTO getUserInfo(HttpSession session) throws Exception {

        log.info("controller 내 정보 가져오기");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        UserInfoDTO uDTO = userInfoService.getUserInfo(userId);

        log.info(uDTO.toString());

        return uDTO;
    }

    /**
     * 내 정보 업데이트
     */
    @ResponseBody
    @PostMapping(value = "updateUserInfo")
    public MsgDTO updateUserInfo(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller 내 정보 업데이트");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String nickname = CmmUtil.nvl(request.getParameter("nickname"));

        log.info("userId : " + userId);
        log.info("nickname : " + nickname);

        int res = 0;
        String msg = "";

        try {
            userInfoService.updateUserInfo(userId, nickname);
            res = 1;
            msg = "수정되었습니다.";

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();

            msg = "오류로 실패하였습니다.\n다시 실행해 주세요.";

        }

        return MsgDTO.builder()
                .result(res)
                .msg(msg)
                .build();
    }

    /**
     * 비밀번호 업데이트
     */
    @ResponseBody
    @PostMapping(value = "updatePassword")
    public MsgDTO updatePassword(HttpSession session, HttpServletRequest request) throws Exception {

        log.info("controller 비밀번호 업데이트");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));
        String newPassword = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("newPassword")));

        log.info("userId : " + userId);
        log.info("email : " + email);
        log.info("userName : " + userName);
        log.info("password : " + password);
        log.info("newPassword : " + newPassword);

        int res = userInfoService.getLogin(userId, password);
        String msg = "";

        if (res == 1) {
            userInfoService.updatePassword(userId, newPassword, email, userName);

            msg = "수정되었습니다.";

        } else {
            msg = "입력하신 정보가 일치하지 않습니다. \n다시 확인해주세요";

        }

        return MsgDTO.builder()
                .result(res)
                .msg(msg)
                .build();
    }

    /**
     * 내 정보 삭제
     */
    @ResponseBody
    @PostMapping(value = "deleteUserInfo")
    public MsgDTO deleteUserInfo(HttpSession session) throws Exception {

        log.info("controller 회원 탈퇴");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        userInfoService.deleteUserInfo(userId);

        session.setAttribute("SS_USER_ID", "");
        session.removeAttribute("SS_USER_ID");

        return MsgDTO.builder()
                .result(1)
                .msg("탈퇴 하였습니다..")
                .build();

    }

    /**
     * 세션 아이디 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getSsUserId")
    public UserInfoDTO getSsUserId(HttpSession session) throws Exception {

        log.info("controller 세션 아이디 가져오기");

        return UserInfoDTO.builder()
                .userId(CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")))
                .build();
    }

    /*
     * 로그아웃
     */
    @ResponseBody
    @PostMapping(value = "logout")
    public MsgDTO logout(HttpSession session) {

        log.info("controller 로그아웃 실행");

        session.setAttribute("SS_USER_ID", "");
        session.removeAttribute("SS_USER_ID");

        return MsgDTO.builder()
                .result(1)
                .msg("로그아웃 하였습니다.")
                .build();
    }
}
