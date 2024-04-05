package kopo.poly.service.impl;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
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
    public String getUserIdExists(String userId) throws Exception {

        log.info("service 아이디 중복 실행");

        String existsYn;

        log.info("userId : " + userId);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            existsYn = "Y";

        } else {
            existsYn = "N";

        }

        log.info("service 아이디 중복확인 종료");

        return existsYn;
    }

    /**
     * 이메일 중복 확인
     */
    @Override
    public String getEmailExists(String email) throws Exception {

        log.info("service 이메일 중복 실행");

        String existsYn;

        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

        if (rEntity.isPresent()) {
            existsYn = "Y";

        } else {
            existsYn = "N";

        }

        log.info("service 이메일 중복확인 종료");

        return existsYn;
    }

    /**
     * 회원 가입
     */
    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info("service 회원가입 실행");

        int res = 0;

        String userId = CmmUtil.nvl(pDTO.userId());
        String userName = CmmUtil.nvl(pDTO.userName());
        String password =  CmmUtil.nvl(pDTO.password());
        String email = CmmUtil.nvl(pDTO.email());

        log.info("pDTO : " + pDTO);

        // 아이디 중복확인
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity. isPresent()) {
            res = 2;

        } else {

            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userName(userName)
                    .userId(userId)
                    .password(password)
                    .email(email)
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

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;

        }

        log.info("service 로그인 종료");

        return res;
    }


}
