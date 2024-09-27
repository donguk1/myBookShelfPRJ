package kopo.poly.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "NLApi", url = "https://www.nl.go.kr/NL/search/openApi/search.do")
public interface NaLibraryAPIFeign {

    @RequestLine("GET ?key={key}&" +
            "pageNum={pageNum}&" +
            "pageSize={pageSize}&" +
            "kwd={kwd}&" +
            "apiType=json&" +
            "systemType=오프라인자료&" +
            "order=asc&" +
            "srchTarget=title")
    String getNlList(
            @Param("key") String key,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("kwd") String kwd
    );
}
