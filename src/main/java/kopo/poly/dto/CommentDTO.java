package kopo.poly.dto;

import kopo.poly.repository.entity.CommentEntity;
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

    public static CommentDTO from(CommentEntity entity) {
        return CommentDTO.builder()
                .commentSeq(entity.getCommentSeq())
                .boardSeq(entity.getBoardSeq())
                .regId(entity.getRegId())
                .contents(entity.getContents())
                .regDt(entity.getRegDt())
                .chgDt(entity.getChgDt())
                .targetSeq(entity.getTargetSeq())
                .dept(entity.getDept())
                .nickname(entity.getUserInfo().getNickname())
                .build();
    }

    public static CommentDTO myComment(CommentEntity entity) {
        return CommentDTO.builder()
                .commentSeq(entity.getCommentSeq())
                .boardSeq(entity.getBoardSeq())
                .regId(entity.getRegId())
                .contents(entity.getContents())
                .regDt(entity.getRegDt())
                .chgDt(entity.getChgDt())
                .targetSeq(entity.getTargetSeq())
                .dept(entity.getDept())
                .build();
    }
}
