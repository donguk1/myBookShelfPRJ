package kopo.poly.controller;

import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.NaverDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.service.INaverService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NaverController {

    private final INaverService naverService;

    private final IUserInfoService userInfoService;

    @GetMapping(value = "/auth/naver/callback")
    public String naverCallback(String code, HttpSession session) throws Exception {

        log.info(".controller 네이버 회원가입 및 로그인 실행");

        int res; // 회원 가입 결과 /// 1 성공, 2 이미 가입

        TokenDTO tokenDTO = naverService.getAccessToken(code);

        log.info("네이버 엑세스 토큰 : " + tokenDTO.access_token());

        NaverDTO naverDTO = naverService.getNaverUserInfo(tokenDTO);

        String userId = "naver_" + naverDTO.response().getId();

        log.info("네이버 아이디 : " + naverDTO.response().getId());

        // 첫 로그인시 회원가입 로직 실행
        if (userInfoService.getUserIdExists(userId) == null) {

            res = userInfoService.insertUserInfo(
                    userId,
                    EncryptUtil.encHashSHA256(userId),
                    naverDTO.response().getEmail(),
                    naverDTO.response().getNickname(),
                    naverDTO.response().getName()
            );

            if (res == 1) {
                log.info("회원가입 성공");

                session.setAttribute("SS_USER_ID", userId);

            } else {
                log.info("회원가입 실패");

            }

        } else {
            log.info("계정 보유로 로그인 실행");

            session.setAttribute("SS_USER_ID", userId);

        }

        log.info(".controller 네이버 회원가입 및 로그인 종료");

        return "/user/login";
    }
}

