package kopo.poly.dto;

import lombok.Builder;

@Builder
public record BoardDTO(

        Long boardSeq,
        String regId,
        String title,
        String noticeYn,
        String category,
        String contents,
        Long readCnt,
        String regDt,
        String chgDt,
        Long commentCnt,
        String fileYn,
        String nickname


) {
}
