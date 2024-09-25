package kopo.poly.service.impl;

import kopo.poly.dto.DataLibraryDTO;
import kopo.poly.dto.LibraryItemDTO;
import kopo.poly.dto.ShoppingDTO;
import kopo.poly.feign.DataLibraryAPIFeign;
import kopo.poly.feign.NaverAPIFeign;
import kopo.poly.feign.SeoulAPIFeign;
import kopo.poly.service.IAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Value("${data.api.encoding.key}")
    private String dataAPIKey;

    private final NaverAPIFeign naverAPIFeign;
    private final SeoulAPIFeign seoulAPIFeign;
    private final DataLibraryAPIFeign dataLibraryAPIFeign;

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

        LibraryItemDTO dto = LibraryItemDTO.builder().build();

        do {
            dto = seoulAPIFeign.getLibraryItem(
                    openAPIClientKey,
                    "json",
                    "SeoulLibraryBookSearchInfo",
                    1,
                    5,
                    title
            );

            log.info(String.valueOf(dto.result().getCode()));
            log.info(String.valueOf(dto.result().getMessage()));
            log.info(String.valueOf(dto.listTotalCount()));

            break;

        }while (dto.result().equals(123));


        return seoulAPIFeign.getLibraryItem(
                openAPIClientKey,
                "json",
                "LibOwndataSmart",
                1,
                5,
                title
        );
    }

    @Override
    public List<DataLibraryDTO> getDataLibraryList(String title, String manageCd) throws Exception {

        log.info("service getDataLibraryList");

        return dataLibraryAPIFeign.getDataLibrary(
                dataAPIKey,
                title,
                manageCd,
                5,
                2
                );
    }
}