package kopo.poly.repository.entity;

import jakarta.persistence.*;
import kopo.poly.repository.entity.PK.BookShelfId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKSHELF")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(BookShelfId.class)
public class BookShelfEntity {

    @Id
    @Column(name = "TITLE")
    private String title;

    @Id
    @Column(name = "REG_ID")
    private String  regId;

    @Column(name = "REG_DT")
    @Temporal(TemporalType.DATE)
    private Date regDt;
}
