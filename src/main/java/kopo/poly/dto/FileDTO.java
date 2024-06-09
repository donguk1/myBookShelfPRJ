package kopo.poly.dto;

import lombok.Builder;

@Builder
public record FileDTO(

        Long fileSeq,           // 저장 순번
        Long boardSeq,        // 커뮤 번호
        Long noticeSeq,
        String saveFileName,    // 저장된 파일명
        String saveFilePath,    // 저장된 파일 경로
        String orgFileName,     // 원본 파일명
        String saveFileUrl     // 저장 경로

) {
}
