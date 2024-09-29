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

    @Id
    @Column(name = "CALL_NO")
    private String callNo;

    @Id
    @Column(name = "REG_ID")
    private String  regId;

    private String title;

    private String manageName;

    private String placeInfo;
}
