package kopo.poly.feign;

import feign.Headers;
import feign.RequestLine;
import feign.Param;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.NaverDTO;
import kopo.poly.dto.TokenDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "naverAuthClient", url = "https://nid.naver.com", configuration = OpenFeignConfig.class)
public interface NaverAuthFeign {

    @RequestLine("POST /oauth2.0/token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    TokenDTO getAccessToken(
            @Param("grant_type") String grantType,
            @Param("client_id") String clientId,
            @Param("client_secret") String clientSecret,
            @Param("redirect_uri") String redirectUri,
            @Param("code") String code
    );

    @RequestLine("POST /v1/nid/me")
    @Headers({
            "Authorization: Bearer {accessToken}",
            "Content-Type: application/x-www-form-urlencoded;charset=utf-8"
    })
    NaverDTO getUserInfo(
            @Param("accessToken") String accessToken
    );
}
