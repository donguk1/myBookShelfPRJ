package kopo.poly.dto;

import lombok.Builder;

@Builder
public record MailDTO(

        String mailSeq,
        String toMail,  // 받는 사람
        String title,   // 메일 제목
        String contents,// 메일 내용
        String sendTime
) {
}
