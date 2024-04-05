package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

    String getUserIdExists(String userId) throws Exception;

    String getEmailExists(String email) throws Exception;

    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    int getLogin(String userId, String password) throws Exception;
}
