package kopo.poly.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKMARK")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class BookmarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKMARK_SEQ", nullable = false)
    private Long bookmarkSeq;

    @Column(name = "BOARD_SEQ", nullable = false)
    private Long boardSeq;

    @Column(name = "USER_ID", nullable = false)
    private String userId;
}
