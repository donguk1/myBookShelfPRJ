package kopo.poly.dto;

import jakarta.persistence.Lob;
import lombok.Builder;

@Builder
public record BookmarkDTO(

        Long bookmarkSeq,
        Long boardSeq,
        String userId
) {
}
