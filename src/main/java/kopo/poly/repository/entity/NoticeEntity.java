package kopo.poly.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Builder
public class NoticeEntity {

    @Id
    private String id;

    private Long noticeSeq;

    private String regId;

    private String title;

    private String contents;

    private String regDt;

    private String chgDt;

    private Long readCnt;
}
