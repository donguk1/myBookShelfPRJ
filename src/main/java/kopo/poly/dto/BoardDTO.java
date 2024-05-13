package kopo.poly.dto;

import kopo.poly.repository.entity.BoardEntity;
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

    public static BoardDTO from(BoardEntity entity) {
        return BoardDTO.builder()
                .boardSeq(entity.getBoardSeq())
                .title(entity.getTitle())
                .regId(entity.getRegId())
                .noticeYn(entity.getNoticeYn())
                .category(entity.getCategory())
                .contents(entity.getContents())
                .readCnt(entity.getReadCnt())
                .regDt(entity.getRegDt())
                .chgDt(entity.getChgDt())
                .commentCnt(entity.getCommentCnt())
                .fileYn(entity.getFileYn())
                .nickname(entity.getNickname())
                .build();
    }
}
