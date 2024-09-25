package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLibraryDTO(

        String bookKey,        // 책 레코드키
        String speciesKey,     // 종 레코드키
        String libName,        // 소장도서관(이름)
        String manageCode,     // 소정도서관(관리구분)
        String regNo,          // 등록번호
        String controlNo,      // 제어번호
        String callNo,         // 청구기호
        String shelfLocName,   // 소장자료실명
        String title,          // 표제
        String vol,            // 편권차 (optional, may be null)
        String volTitle,       // 편제 (optional, may be null)
        String author,         // 저작자
        String publisher,      // 발행자
        String pubYear,        // 발행년도
        String appendixYn,     // 부록유무 (Y, N)
        String isbn,           // 낱권ISBN
        int rnum               // 결과순번

        
) {
}
