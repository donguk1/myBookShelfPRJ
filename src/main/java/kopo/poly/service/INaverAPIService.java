package kopo.poly.service;

import feign.Param;
import feign.RequestLine;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.ShoppingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "https://openapi.naver.com", configuration = OpenFeignConfig.class)
public interface INaverAPIService {

    @RequestLine("GET /v1/search/book.json?query={title}&display={display}&sort={sort}")
    ShoppingDTO getShoppingList(
            @Param("title") String title,
            @Param("display") int display,
            @Param("sort") String sort
    );
}
