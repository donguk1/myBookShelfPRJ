package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // Json 파싱 시 없는 필드 무시
public record ShoppingDTO(
        String lastBuildData,
        String total,
        String start,
        String display,
        List<item> items
) {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class item {
        private String title;
        private String link;
        private String image;
        private String author;
        private Long discount;
        private String publisher;
        private Long isbn;
        private String description;
        private String pubdate;

    }
}
