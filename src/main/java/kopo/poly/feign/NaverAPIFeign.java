package kopo.poly.feign;

import feign.Param;
import feign.RequestLine;
import kopo.poly.config.OpenFeignConfig;
import kopo.poly.dto.ShoppingDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "naverApiClient", url = "https://openapi.naver.com", configuration = OpenFeignConfig.class)
public interface NaverAPIFeign {

    @RequestLine("GET /v1/search/book.json?query={title}&display={display}&sort={sort}")
    ShoppingDTO getShoppingList(
            @Param("title") String title,
            @Param("display") int display,
            @Param("sort") String sort
    );
}
