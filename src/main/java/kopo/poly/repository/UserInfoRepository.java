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
    Optional<UserInfoEntity> findByUserIdAndPassword(String userId,
                                                     String password);

    /**
     * 아이디 찾기
     */
    Optional<UserInfoEntity> findByUserNameAndEmail(String userName,
                                                    String email);

    /**
     * 비번 찾기
     */
    Optional<UserInfoEntity> findByUserIdAndUserNameAndEmail(String userId,
                                                             String userName,
                                                             String email);


}
