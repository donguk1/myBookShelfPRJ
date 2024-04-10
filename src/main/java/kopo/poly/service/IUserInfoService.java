package kopo.poly.service;

public interface IUserInfoService {

    /**
     * 아이디 중복 확인
     */
    String getUserIdExists(final String userId) throws Exception;

    /**
     * 이메일 중복 확인
     */
    String getEmailExists(final String email) throws Exception;

    /**
     * 회원 가입
     */
    int insertUserInfo(final String userId,
                       final String password,
                       final String email,
                       final String nickname,
                       final String userName) throws Exception;

    /**
     * 로그인
     */
    int getLogin(final String userId,
                 final String password) throws Exception;

    /**
     * 아이디 찾기
     */
    String findId(final String userName,
                  final String email) throws Exception;

    /**
     * 비번 찾기
     */
    int findPassword(final String userId,
                     final String userName,
                     final String email)throws Exception;

    /**
     * 비번 업데이트
     */
    void updatePassword(final String userId,
                        final String password,
                        final String email,
                        final String userName) throws Exception;

}
