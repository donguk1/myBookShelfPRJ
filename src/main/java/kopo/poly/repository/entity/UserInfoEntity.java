package kopo.poly.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class UserInfoEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private String userId;

    @NonNull
    @Column(name = "user_name", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @NonNull
    @Column(name = "nickname", nullable = false)
    private String nickName;

}
