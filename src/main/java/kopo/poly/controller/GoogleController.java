package kopo.poly.controller;

import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.GoogleUserDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.service.IGoogleService;
import kopo.poly.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GoogleController {

    private final IGoogleService googleService;

    private final IUserInfoService userInfoService;

    @GetMapping("/login/oauth2/code/google")
    public String googleCallback(String code, HttpSession session) throws Exception {

        log.info("controller googleCallback");

        int res;

        TokenDTO tokenDTO = googleService.getAccessToken(code);

        log.info("accessToken : {}", tokenDTO.access_token());

        GoogleUserDTO gDTO = googleService.getGoogleUserInfo(tokenDTO);

        log.info(gDTO.toString());

        return null;
    }
}
