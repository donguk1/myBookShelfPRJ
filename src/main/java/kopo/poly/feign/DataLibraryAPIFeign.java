package kopo.poly.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "DataLibrary", url = "http://openapi-lib.sen.go.kr/openapi/service/lib/openApi")
public interface DataLibraryAPIFeign {

    @RequestLine("GET ?serviceKey={serviceKey}" +
            "&title={title}" +
            "&manageCd={manageCd}&" +
            "numOfRows={numOfRows}&" +
            "pageNo={pageNo}")
    String getDataLibrary(
            @Param("serviceKey") String serviceKey,
            @Param("title") String title,
            @Param("manageCd") String manageCd,
            @Param("numOfRows") int numOfRows,
            @Param("pageNo") int pageNo
    );
}
