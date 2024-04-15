package kopo.poly.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOARD")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class BoardEntity {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;
    

    @Column(name = "REG_ID", length = 500, nullable = false)
    private String regId;

    @Column(name = "TITLE", length = 25, nullable = false)
    private String title;

    @Column(name = "NOTICE_YN", length = 3, nullable = false)
    private String noticeYn;

    @Column(name = "CATEGORY", length = 10, nullable = false)
    private String category;

    @Column(name = "CONTENTS", length = 5000, nullable = false)
    private String contents;

    @Column(name = "READ_CNT", nullable = false)
    Long readCnt;

    @Column(name = "REG_DT", nullable = false)
    private String regDt;

    @Column(name = "CHG_DT", nullable = false)
    private String chgDt;
}
