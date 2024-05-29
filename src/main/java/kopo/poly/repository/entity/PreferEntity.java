package kopo.poly.repository.entity;

import jakarta.persistence.*;
import kopo.poly.repository.entity.PK.PreferPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PREFER")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(PreferPK.class)
public class PreferEntity {

    @Id
    @Column(name = "REG_ID")
    private String regId;

    @Id
    @Column(name = "CATEGORY")
    private String category;
}
