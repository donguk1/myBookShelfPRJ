package kopo.poly.dto;

import lombok.Builder;

@Builder
public record CommentDTO(

        Long commentSeq,
        Long boardSeq,
        String regId,
        String contents,
        String regDt,
        String chgDt,
        Long targetSeq,
        int dept,
        String nickname
) {
}
