package kopo.poly.feign;

import feign.Param;
import feign.RequestLine;
import kopo.poly.dto.DataLibraryDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "DataLibrary", url = "http://openapi-lib.sen.go.kr/openapi/service/lib/openApi")
public interface DataLibraryAPIFeign {

    @RequestLine("GET ?key={serviceKey}&title={title}&manageCd={manageCd}&numOfRows={numOfRows}&pageNo={pageNo}")
    List<DataLibraryDTO> getDataLibrary(
            @Param("serviceKey") String serviceKey,
            @Param("title") String title,
            @Param("manageCd") String manageCd,
            @Param("numOfRows") int numOfRows,
            @Param("pageNo") int pageNo
    );
}
