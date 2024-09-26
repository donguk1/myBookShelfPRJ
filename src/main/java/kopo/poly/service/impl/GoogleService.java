package kopo.poly.service.impl;

import kopo.poly.dto.GoogleUserDTO;
import kopo.poly.dto.TokenDTO;
import kopo.poly.feign.GoogleAuthFeign;
import kopo.poly.service.IGoogleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleService implements IGoogleService {

    private final GoogleAuthFeign googleAuthFeign;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientKey;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleSecretKey;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Override
    public TokenDTO getAccessToken(String code) throws Exception {

        log.info("service getAccessToken");

        return googleAuthFeign.getAccessToken(
                "authorization_code",
                googleClientKey,
//                googleSecretKey,
                googleRedirectUri,
                code
        );
    }

    @Override
    public GoogleUserDTO getGoogleUserInfo(TokenDTO pDTO) throws Exception {

        log.info("service getGoogleUserInfo");

        return null;
    }
}
