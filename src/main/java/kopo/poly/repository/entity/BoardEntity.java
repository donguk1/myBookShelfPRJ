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
    @Column(name = "board_seq")
    private Long boardSeq;
    
    @NonNull
    @Column(name = "reg_id", length = 500, nullable = false)        
    private String regId;
    
    @NonNull
    @Column(name = "title", length = 25, nullable = false)
    private String title;

    @NonNull
    @Column(name = "notice_yn", length = 3, nullable = false)
    private String noticeYn;

    @NonNull
    @Column(name = "category", length = 10, nullable = false)
    private String category;

    @NonNull
    @Column(name = "contents", length = 5000, nullable = false)
    private String contents;

    @Column(name = "read_cnt")
    Long readCnt;

    @Column(name = "reg_dt")
    private String regDt;

    @Column(name = "chg_dt")
    private String chgDt;
}
