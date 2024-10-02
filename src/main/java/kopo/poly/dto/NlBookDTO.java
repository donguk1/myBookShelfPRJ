package kopo.poly.dto;

import lombok.Builder;

@Builder
public record NlBookDTO(

        String callNo,
        String id,
        String regId,
        String title,
        String manageName,
        String placeInfo

) {
}
