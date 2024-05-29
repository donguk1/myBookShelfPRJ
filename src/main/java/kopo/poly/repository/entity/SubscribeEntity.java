package kopo.poly.repository.entity;

import jakarta.persistence.*;
import kopo.poly.repository.entity.PK.SubscribePK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUBSCRIBE")
@Entity
@IdClass(SubscribePK.class)
public class SubscribeEntity {

    @Id
    @Column(name = "REG_ID")
    private String regId;

    @Id
    @Column(name = "TARGET_ID")
    private String targetId;

    @Column(name = "REG_NICKNAME")
    private String regNickname;

    @Column(name = "TARGET_NICKNAME")
    private String targetNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false)
    private UserInfoEntity userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_ID", insertable = false, updatable = false)
    private UserInfoEntity targetUserInfo;
}
