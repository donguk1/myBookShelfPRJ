package kopo.poly.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.NaverDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "naverAuthClient", url = "https://openapi.naver.com", configuration = OpenFeignConfig.class)
public interface NaverLoginFeign {

    @RequestLine("POST /v1/nid/me")
    @Headers({
            "Authorization: Bearer {accessToken}",
            "Content-Type: application/x-www-form-urlencoded;charset=utf-8"
    })
    NaverDTO getUserInfo(
            @Param("accessToken") String accessToken
    );
}
