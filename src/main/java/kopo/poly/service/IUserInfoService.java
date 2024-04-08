package kopo.poly.service;

public interface IUserInfoService {

    String getUserIdExists(String userId) throws Exception;

    String getEmailExists(String email) throws Exception;

    int insertUserInfo(final String userId,
                       final String password,
                       final String email,
                       final String nickname,
                       final String userName) throws Exception;

    int getLogin(String userId, String password) throws Exception;
}
