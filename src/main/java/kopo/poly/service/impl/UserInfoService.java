package kopo.poly.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {
    

    private final UserInfoRepository userInfoRepository;


    /**
     * 아이디 중복 확인
     */
    @Override
    public String getUserIdExists(final String userId) throws Exception {

        log.info("service 아이디 중복 실행");

        String existsYn;

        log.info("userId : " + userId);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            existsYn = "N";

        } else {
            existsYn = "Y";

        }

        log.info("service 아이디 중복확인 종료");

        return existsYn;
    }

    /**
     * 이메일 중복 확인
     */
    @Override
    public String getEmailExists(final String email) throws Exception {

        log.info("service 이메일 중복 실행");

        String existsYn;

        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

        if (rEntity.isPresent()) {
            existsYn = "N";

        } else {
            existsYn = "Y";

        }

        log.info("service 이메일 중복확인 종료");

        return existsYn;
    }

    /**
     * 회원 가입
     */
    @Override
    public int insertUserInfo(String userId, String password, String email, String nickname, String userName) throws Exception {

        log.info("service 회원가입 실행");

        int res = 0;

        // 아이디 중복확인
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity. isPresent()) {
            res = 2;

        } else {

            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .userName(userName)
                    .build();

            userInfoRepository.save(pEntity);

            rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) {
                res = 1;
                
            }
            
        }

        log.info("service 회원가입 종료");

        return res;
    }

    /**
     * 로그인 
     */
    @Override
    public int getLogin(String userId, String password) throws Exception {

        log.info("service 로그인 실행");

        int res = 0;

        log.info("userId : " + userId);
        log.info("password : " + password);

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;

        }

        log.info("service 로그인 종료");

        return res;
    }

    /**
     * 아이디 찾기
     */
    @Override
    public String findId(String userName, String email) throws Exception {

        log.info("service 아이디 찾기 실행");
        log.info("userName : " + userName);
        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserNameAndEmail(userName, email);

        String userId = null;

        if (rEntity.isPresent()) {
            userId = rEntity.get().getUserId();

        }

        return userId;
    }

    /**
     * 비번 찾기
     */
    @Override
    public int findPassword(String userId, String userName, String email) throws Exception {

        log.info("service 비번 찾기 실행");

        int res = 0;

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserIdAndUserNameAndEmail(userId, userName, email);

        if (rEntity.isPresent()) {
            res = 1;

        }

        return res;
    }

    /**
     * 비번 업데이트
     */
    @Override
    public void updatePassword(String userId, String password, String userName, String email) throws Exception {

        log.info("service 비번 업데이트 실행");

        Optional<UserInfoEntity> pEntity = userInfoRepository.findByUserId(userId);

        UserInfoEntity rEntity = UserInfoEntity.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .email(email)
                .nickname(pEntity.get().getNickname())
                .build();

        userInfoRepository.save(rEntity);
    }

    /**
     * 내 정보 가져오기
     */
    @Override
    public UserInfoDTO getUserInfo(String userId) throws Exception {

        log.info("service 내 정보 가져오기 실행");

        Optional<UserInfoEntity> pEntity = userInfoRepository.findByUserId(userId);

        UserInfoDTO uDTO;

        if (pEntity.isPresent()) {
            uDTO = new ObjectMapper().convertValue(pEntity.get(),
                    new TypeReference<UserInfoDTO>() {
                    });

        } else {
            uDTO = UserInfoDTO.builder()
                    .build();

        }

        return uDTO;
    }

    /**
     * 내 정보 업데이트
     */
    @Override
    public void updateUserInfo(String userId, String nickname) throws Exception {

        log.info("service 내 정보 업데이트");

        Optional<UserInfoEntity> pEntity = userInfoRepository.findByUserId(userId);

        UserInfoEntity rEntity = UserInfoEntity.builder()
                .userId(userId)
                .email(pEntity.get().getEmail())
                .nickname(nickname)
                .userName(pEntity.get().getUserName())
                .password(pEntity.get().getPassword())
                .build();

        userInfoRepository.save(rEntity);
    }

    /**
     * 내 정보 삭제
     */
    @Transactional
    @Override
    public void deleteUserInfo(String userId) throws Exception {

        log.info("controller 회원 탈퇴");

        userInfoRepository.deleteById(userId);

    }
}
