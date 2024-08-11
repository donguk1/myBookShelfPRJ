package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.ShoppingDTO;
import kopo.poly.service.INaverAPIService;
import kopo.poly.service.INaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverService implements INaverService {


    // application.yml 파일의 해당하는 값을 주입
    @Value("${naver.client_id}")
    private String naverClientId;
    @Value("${naver.client_secret}")
    private String naverClientSecret;

    private final INaverAPIService naverAPIService;

    @Override
    public ShoppingDTO getShoppingList(String title) throws Exception {

        log.info("service getShoppingList");

//        // Feign 클라이언트를 이용한 API 호출
//        ShoppingDTO response
//
//        // 요청 헤더 설정
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", naverClientId);
//        requestHeaders.put("X-Naver-Client-Secret", naverClientSecret);
//
//        // api 요청 및 응답
//        String responseBody = get(apiUrl, requestHeaders);
//
//        log.info(responseBody);
//
//        // 응답 본문을 ShoppingDTO 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();

        return naverAPIService.getShoppingList(
                title,
                1, // display
                "sim" // sort
        );
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){

        // API URL 연결
        HttpURLConnection con = connect(apiUrl);

        try {
            // Method 형식 지정
            con.setRequestMethod("GET");

            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                // 요청 헤더 설정
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode(); // 응답 헤더

            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출 시 True
                // 응답 본문 반환
                return readBody(con.getInputStream());

            } else { // 오류 발생
                // 오류 응답 본문 반환
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);

            // HttpURLConnection 객체 생성
            return (HttpURLConnection)url.openConnection();

        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);

        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){


        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {

            StringBuilder responseBody = new StringBuilder();
            String line;

            // 한 줄씩 StringBuilder에 추가
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            // 응답 본문 문자열로 반환
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}