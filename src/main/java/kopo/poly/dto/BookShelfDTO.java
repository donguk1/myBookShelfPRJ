package kopo.poly.dto;

import kopo.poly.repository.entity.BookShelfEntity;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record BookShelfDTO(

        String title,
        String regId,
        LocalDate regDt
) {

    public static BookShelfDTO from(BookShelfEntity entity) {
        return BookShelfDTO.builder()
                .title(entity.getTitle())
                .regId(entity.getRegId())
                .regDt(entity.getRegDt().toLocalDate())
                .build();
    }
}
