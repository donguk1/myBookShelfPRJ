package kopo.poly.repository;

import kopo.poly.repository.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {


    /**
     * 아이디 중복 확인
     */
    Optional<UserInfoEntity> findByUserId(String userId);

    /**
     * 이메일 중복 확인
     */
    Optional<UserInfoEntity> findByEmail(String email);

    /**
     * 로그인
     */
    Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String password);
}
