package kopo.poly.dto;

import lombok.Builder;

@Builder
public record NoticeDTO(

        Long noticeSeq,
        String regId,
        String title,
        String contents,
        Long readCnt,
        String regDt,
        String chgDt

) {

}
