package kopo.poly.service.impl;

import kopo.poly.dto.KakaoDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.feign.KakaoAuthFeign;
import kopo.poly.feign.KakaoLoginFeign;
import kopo.poly.service.IKakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoService implements IKakaoService {

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.href}")
    private String kakaoHref;

    private final KakaoAuthFeign kakaoAuthFeign;

    private final KakaoLoginFeign kakaoLoginFeign;

    /* 토큰 가져오기 */
    @Override
    public TokenDTO getAccessToken(String code) throws Exception {

        log.info("service getAccessToken");

        return kakaoAuthFeign.getAccessToken(
                "authorization_code",
                kakaoClientId,
                kakaoRedirectUri,
                code
        );
    }

    /* 카카오에서 정보 가져오기 */
    @Override
    public KakaoDTO getKakaoUserInfo(TokenDTO pDTO) throws Exception {

        log.info("service getKakaoUserInfo");

        return kakaoLoginFeign.getUserInfo(pDTO.access_token());
    }

    @Override
    public void deleteToken(String accessToken) throws Exception {

        log.info("service deleteToken");

        kakaoLoginFeign.deleteToken(
                accessToken
        );
    }
}
