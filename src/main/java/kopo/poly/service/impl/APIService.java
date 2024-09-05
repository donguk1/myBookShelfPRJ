package kopo.poly.service.impl;

import kopo.poly.dto.LibraryItemDTO;
import kopo.poly.dto.ShoppingDTO;
import kopo.poly.feign.NaverAPIFeign;
import kopo.poly.feign.SeoulAPIFeign;
import kopo.poly.service.IAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class APIService implements IAPIService {

    @Value("${naver.client_id}")
    private String naverClientId;

    @Value("${naver.client_secret}")
    private String naverClientSecret;

    @Value("${openapi.client.key}")
    private String openAPIClientKey;

    private final NaverAPIFeign naverAPIFeign;
    private final SeoulAPIFeign seoulAPIFeign;

    @Override
    public ShoppingDTO getShoppingList(String title) throws Exception {

        log.info("service getShoppingList");

        return naverAPIFeign.getShoppingList(
                title,
                1, // display
                "sim" // sort
        );
    }

    @Override
    public LibraryItemDTO getLibraryItem(String title) throws Exception {

        log.info("service getLibraryItem");

        return seoulAPIFeign.getLibraryItem(
                openAPIClientKey,
                "json",
                "LibOwndataSmart",
                1,
                5,
                title
        );
    }
}