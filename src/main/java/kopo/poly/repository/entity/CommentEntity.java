package kopo.poly.repository.entity;


import jakarta.persistence.*;
import lombok.*;
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
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    @NonNull
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @NonNull
    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "DEPT")
    private int dpet;

    @Column(name = "TARGET_SEQ")
    private Long targetSeq;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "CHG_DT")
    private String chgDt;
}
