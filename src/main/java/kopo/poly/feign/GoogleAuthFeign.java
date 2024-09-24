package kopo.poly.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import kopo.poly.dto.TokenDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "googleAuth", url = "https://oauth2.googleapis.com")
public interface GoogleAuthFeign {

    @RequestLine("POST /token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    TokenDTO getAccessToken(
            @Param("grant_type") String grantType,
            @Param("client_id") String clientId,
//            @Param("client_secret") String clientSecret,
            @Param("redirect_uri") String redirectUri,
            @Param("code") String code
    );
}
