package kopo.poly.feign;

import feign.RequestLine;
import feign.Param;
import kopo.poly.dto.LibraryItemDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "seoulApiClient", url = "http://openapi.seoul.go.kr:8088")
public interface SeoulAPIFeign {

    @RequestLine("GET /{apiKey}/{fileType}/{serviceName}/{startIndex}/{endIndex}/{dataTtl}")
    LibraryItemDTO getLibraryItem(
            @Param("apiKey") String apiKey,
            @Param("fileType") String fileType,
            @Param("serviceName") String serviceName,
            @Param("startIndex") int startIndex,
            @Param("endIndex") int endIndex,
            @Param("dataTtl") String date
    );
}
