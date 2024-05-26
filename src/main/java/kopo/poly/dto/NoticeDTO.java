package kopo.poly.dto;

import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.NoticeEntity;
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

    public static NoticeDTO from(NoticeEntity entity) {
        return NoticeDTO.builder()
                .noticeSeq(entity.getNoticeSeq())
                .title(entity.getTitle())
                .regId(entity.getRegId())
                .contents(entity.getContents())
                .readCnt(entity.getReadCnt())
                .regDt(entity.getRegDt())
                .chgDt(entity.getChgDt())
                .build();
    }
}
