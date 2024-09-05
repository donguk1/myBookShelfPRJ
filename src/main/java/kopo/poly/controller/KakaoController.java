package kopo.poly.controller;


import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.KakaoDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.service.IKakaoService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final IKakaoService kakaoService;

    private final IUserInfoService userInfoService;

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code, HttpSession session) throws Exception {

        log.info(".controller 카카오 로그인 시작");

        int res; // 회원 가입 결과 /// 1 성공, 2 이미 가입

        TokenDTO tokenDTO = kakaoService.getAccessToken(code);

        log.info("카카오 엑세스 토큰 : " + tokenDTO.access_token());

        KakaoDTO kakaoDTO = kakaoService.getKakaoUserInfo(tokenDTO);
        String userId = "kakao_" + kakaoDTO.id();

        log.info("kakaoDTO : " + kakaoDTO);
        log.info("카카오 아이디 : " + userId);

        if (userInfoService.getUserIdExists(userId) == null) {

            res = userInfoService.insertUserInfo(
                    userId,
                    EncryptUtil.encHashSHA256(userId),
                    kakaoDTO.kakao_account().getEmail(),
                    kakaoDTO.properties().getNickname(),
                    kakaoDTO.kakao_account().getName()
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

        log.info(".controller 카카오 회원가입 및 로그인 종료");

        return "/user/login";

    }
}
