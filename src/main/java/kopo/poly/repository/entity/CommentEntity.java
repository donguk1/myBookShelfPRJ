package kopo.poly.repository.entity;


import jakarta.persistence.*;
import kopo.poly.repository.entity.PK.CommentPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(CommentPK.class)
public class CommentEntity {

    @Id
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @Id
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "DEPT")
    private int dept;

    @Column(name = "TARGET_SEQ")
    private Long targetSeq;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "CHG_DT")
    private String chgDt;

    @Column(name = "NICKNAME")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false)
    private UserInfoEntity userInfo;
}


