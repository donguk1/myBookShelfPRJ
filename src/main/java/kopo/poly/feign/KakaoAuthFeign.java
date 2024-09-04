package kopo.poly.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.KakaoDTO;
import kopo.poly.dto.TokenDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "KakaoAuthClient", url = "https://kauth.kakao.com", configuration = OpenFeignConfig.class)
public interface KakaoAuthFeign {

    @RequestLine("POST /oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    TokenDTO getAccessToken(
            @Param("grant_type") String grantType,
            @Param("client_id") String clientId,
            @Param("redirect_uri") String redirectUri,
            @Param("code") String code
    );


}

