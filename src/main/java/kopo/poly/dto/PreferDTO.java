package kopo.poly.dto;

import kopo.poly.repository.entity.PreferEntity;
import lombok.Builder;

@Builder
public record PreferDTO(

        String regId,
        String category
) {

    public static PreferDTO from(PreferEntity entity) {
        return PreferDTO.builder()
                .regId(entity.getRegId())
                .category(entity.getCategory())
                .build();
    }

}
