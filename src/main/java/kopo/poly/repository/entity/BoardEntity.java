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
    // PK 자동생성하기 위한 어노테이션
    // DB가 MySQL 기반인 MariaDB 이기에 IDENTITY 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @Column(name = "REG_ID", length = 500, nullable = false, updatable = false)
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
    private Long readCnt;

    @Column(name = "REG_DT", nullable = false, updatable = false)
    private String regDt;

    @Column(name = "CHG_DT", nullable = false)
    private String chgDt;

    @Column(name = "COMMENT_CNT")
    private Long commentCnt;

    @Column(name = "FILE_YN")
    private String fileYn;

    @Column(name = "NICKNAME")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계, 리연로딩
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false) // FK열 지정과 삽입 및 수정 시 제외
    private UserInfoEntity userInfo;
}
