package kopo.poly.service;

import kopo.poly.dto.GoogleUserDTO;
import kopo.poly.dto.TokenDTO;

public interface IGoogleService {

    TokenDTO getAccessToken(String code) throws Exception;

    GoogleUserDTO getGoogleUserInfo(TokenDTO pDTO) throws Exception;
}
