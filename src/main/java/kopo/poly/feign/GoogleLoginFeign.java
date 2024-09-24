package kopo.poly.feign;

import feign.Param;
import feign.RequestLine;
import kopo.poly.dto.GoogleUserDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "googleUser", url="https://www.googleapis.com")
public interface GoogleLoginFeign {

    @RequestLine("POST /userinfo/v2/me")
    GoogleUserDTO getGoogleUserInfo(
            @Param("accessToken") String accessToken
    );
}
