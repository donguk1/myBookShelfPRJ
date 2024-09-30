package kopo.poly.repository.entity;

import jakarta.persistence.*;
import kopo.poly.repository.entity.PK.NlBookPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NLBOOK")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(NlBookPK.class)
public class NlBookEntity {


    @Column(name = "CALL_NO")
    private String callNo;

    @Id
    @Column(name = "ID")
    private String id;

    @Id
    @Column(name = "REG_ID")
    private String  regId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "MANAGE_NAME")
    private String manageName;

    @Column(name = "PLACE_INFO")
    private String placeInfo;
}
