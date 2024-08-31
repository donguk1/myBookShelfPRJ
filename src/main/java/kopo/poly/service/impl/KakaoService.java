package kopo.poly.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.KakaoDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.service.IKakaoService;
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

    /* 토큰 가져오기 */
    @Override
    public TokenDTO getAccessToken(String code) throws Exception {

        log.info(".service 카카오 토큰 가져오기 실행");

        /*
         * POST 방식으로 key=value 형식으로 데이터 요청(카카오 쪽으로)
         * http 요청을 편하게 할수 있게하는 Retrofit2, OkHttp, RestTemplate 라이브러리가 있다
         */
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        log.info("code : " + code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // 객체에 담아볼 것이다. Gson, Json Simple, ObjectMapper 라이브러리가 있다.
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDTO tokenDTO = null;

        try {
            tokenDTO = objectMapper.readValue(response.getBody(), TokenDTO.class);

        } catch (JsonMappingException e) {
            log.info(e.toString());
            e.printStackTrace();    // Exception 발생 이유와 위치는 어디에서 발생했는지 전체적인 단계 출력

        } catch (JsonProcessingException e) {
            log.info(e.toString());
            e.printStackTrace();    // Exception 발생 이유와 위치는 어디에서 발생했는지 전체적인 단계 출력

        }

        log.info(".service 카카오 토큰 가져오기 종료");

        return tokenDTO;
    }

    /* 카카오에서 정보 가져오기 */
    @Override
    public KakaoDTO getKakaoUserInfo(TokenDTO pDTO) throws Exception {

        log.info(".service 카카오에서 유저 정보 가져오기 실행");

        // http 요청을 편하게 할 수 있다. Retrofit2, OkHttp, RestTemplate 라이브러리가 있다.
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + pDTO.access_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>>kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDTO kakaoDTO = null;

        try {
            kakaoDTO = objectMapper.readValue(response2.getBody(), KakaoDTO.class);
            log.info("kakaoDTO : " + kakaoDTO);

            /*  실패 사유 확인용 로그  */
        } catch (JsonMappingException e) {
            log.info(e.toString());
            e.printStackTrace();    // Exception 발생 이유와 위치는 어디에서 발생했는지 전체적인 단계 출력

        } catch (JsonProcessingException e) {
            log.info(e.toString());
            e.printStackTrace();    // Exception 발생 이유와 위치는 어디에서 발생했는지 전체적인 단계 출력

        }

        return kakaoDTO;
    }
}
