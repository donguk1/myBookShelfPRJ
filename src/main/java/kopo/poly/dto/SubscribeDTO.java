package kopo.poly.dto;

import kopo.poly.repository.entity.SubscribeEntity;
import lombok.Builder;

@Builder
public record SubscribeDTO(

        String regId,

        String targetId,

        String regNickname,
        String targetNickname


) {

    public static SubscribeDTO from(SubscribeEntity entity) {
        return SubscribeDTO.builder()
                .regId(entity.getRegId())
                .targetId(entity.getTargetId())
                .regNickname(entity.getRegNickname())
                .targetNickname(entity.getTargetNickname())
                .build();
    }
}
