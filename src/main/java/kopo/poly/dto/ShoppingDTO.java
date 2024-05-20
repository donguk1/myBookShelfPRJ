package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShoppingDTO(
        String lastBuildData,
        String total,
        String start,
        String display,
        List<item> items
) {

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class channel{
//        private DateTime lastBuildData;
//        private int total;
//        private int start;
//        private int display;
//        List<item> items;
//    }

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
