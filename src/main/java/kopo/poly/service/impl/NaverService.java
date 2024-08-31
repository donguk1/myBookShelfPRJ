package kopo.poly.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.NaverDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.feign.NaverAuthFeign;
import kopo.poly.service.INaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverService implements INaverService {

    @Value("${naver.client_id}")
    private String naverClientId;
    @Value("${naver.client_secret}")
    private String naverClientSecret;
    @Value("${naver.redirect_uri}")
    private String naverRedirectUri;

    private final NaverAuthFeign naverAuthFeign;
    
    /* 토큰 가져오기 */
    @Override
    public TokenDTO getAccessToken(String code) throws Exception {

        log.info(".service 네이버 토큰 가져오기 실행");

        return naverAuthFeign.getAccessToken(
                "authorization_code",
                naverClientId,
                naverClientSecret,
                naverRedirectUri,
                code
        );
    }

    /* 네이버에서 정보 가져오기 */
    @Override
    public NaverDTO getNaverUserInfo(TokenDTO pDTO) throws Exception {

        log.info(".service 네이버에서 유저 정보 가져오기 실행");

        return naverAuthFeign.getUserInfo(pDTO.access_token());
    }
}
