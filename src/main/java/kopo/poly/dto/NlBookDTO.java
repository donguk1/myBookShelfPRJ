package kopo.poly.dto;

import kopo.poly.repository.entity.NlBookEntity;
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

    public static NlBookDTO from(NlBookEntity entity) {
        return NlBookDTO.builder()
                .callNo(entity.getCallNo())
                .id(entity.getId())
                .regId(entity.getRegId())
                .title(entity.getTitle())
                .manageName(entity.getManageName())
                .placeInfo(entity.getPlaceInfo())
                .build();
    }
}
