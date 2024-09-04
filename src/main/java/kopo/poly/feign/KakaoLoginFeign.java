package kopo.poly.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.KakaoDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "KakaoAuthClient", url = "https://kapi.kakao.com", configuration = OpenFeignConfig.class)
public interface KakaoLoginFeign {

    @RequestLine("POST /v2/user/me")
    @Headers({
            "Authorization: Bearer {accessToken}",
            "Content-Type: application/x-www-form-urlencoded;charset=utf-8"
    })
    KakaoDTO getUserInfo(
            @Param("accessToken") String accessToken
    );
}