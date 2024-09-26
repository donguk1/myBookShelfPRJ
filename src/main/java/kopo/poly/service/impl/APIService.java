package kopo.poly.service.impl;

import kopo.poly.dto.LibraryItemDTO;
import kopo.poly.dto.ShoppingDTO;
import kopo.poly.feign.DataLibraryAPIFeign;
import kopo.poly.feign.NaLibraryAPIFeign;
import kopo.poly.feign.NaverAPIFeign;
import kopo.poly.feign.SeoulAPIFeign;
import kopo.poly.service.IAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

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

    @Value("${data.api.decoding.key}")
    private String dataAPIDecodeKey;

    @Value("${nl.api.key}")
    private String nlApiKey;

    private final NaverAPIFeign naverAPIFeign;
    private final SeoulAPIFeign seoulAPIFeign;
    private final DataLibraryAPIFeign dataLibraryAPIFeign;
    private final NaLibraryAPIFeign naLibraryAPIFeign;

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

            log.info(dto.toString());

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
    public String getDataLibraryList(String title, String manageCd) throws Exception {

        log.info("service getDataLibraryList");

        StringBuilder urlBuilder = new StringBuilder("http://openapi-lib.sen.go.kr/openapi/service/lib/openApi"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +
                URLDecoder.decode("ZQfExCgKUdJkw/Oz3zDtV94ll2CIVw+hTYucvQA9IanxQa66kDo2v/a8jFjITZVZAX6EKxksAqv4NThOMro+Rw==", "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("title","UTF-8") + "=" + URLEncoder.encode("도둑", "UTF-8")); /*도서제목검색어 (*필수항목*)*/
        urlBuilder.append("&" + URLEncoder.encode("manageCd","UTF-8") + "=" + URLEncoder.encode("MB", "UTF-8")); /*도서관코드(*필수항목*)*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*출력건수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")); /*출력페이지*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return dataLibraryAPIFeign.getDataLibrary(
//                URLDecoder.decode(dataAPIKey, "UTF-8"),
                URLEncoder.encode(dataAPIDecodeKey, "UTF-8"),
                title,
                manageCd,
                5,
                2
                );
    }

    @Override
    public String getNlList(String title) throws Exception {

        log.info("service getNlList");

        return naLibraryAPIFeign.getNlList(
                nlApiKey,
                1,
                10,
                title
        );
    }
}