package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // Json 파싱 시 없는 필드 무시
public record LibraryItemDTO(

        int listTotalCount,           // 총 데이터 건수
        Result result,                // 요청결과 (Result 클래스 사용)
        String dataCd,                // 자료코드
        String regNo,                 // 등록번호
        String dataTtl,               // 자료명
        String aut,                   // 저자
        String publer,                // 출판사
        String pblcnYr,               // 발행년
        String clmNo,                 // 청구기호
        String clsfNo,                // 분류기호
        String lang,                  // 언어
        String langNm,                // 언어명
        String ntnNm,                 // 국가명
        String ownshpCd,              // 소장처
        String ownshpNm,              // 소장처명
        String lctnCd,                // 자료실 코드
        String lctnNm,                // 자료실 이름
        String loanStts,              // 대출상태
        String loanSttsMsg            // 대출상태메시지
) {
    @Data
    public static class Result {
        private final int code;
        private final String message;

    }
}

