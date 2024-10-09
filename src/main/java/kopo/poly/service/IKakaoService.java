package kopo.poly.service;


import kopo.poly.dto.KakaoDTO;
import kopo.poly.dto.TokenDTO;

public interface IKakaoService {

    TokenDTO getAccessToken(String code) throws Exception;

    KakaoDTO getKakaoUserInfo(TokenDTO pDTO) throws Exception;

    void deleteToken(String accessToken) throws Exception;

}
