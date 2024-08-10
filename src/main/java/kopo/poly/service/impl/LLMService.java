package kopo.poly.service.impl;


import kopo.poly.repository.PreferRepository;
import kopo.poly.repository.entity.PreferEntity;
import kopo.poly.service.ILLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LLMService implements ILLMService {

    // RestTemplate 인스턴스 생성
    private final RestTemplate restTemplate = new RestTemplate();

    private final PreferRepository preferRepository;

    @Override
    public ResponseEntity<Map> getLLMData() throws Exception {

        log.info("service getLLMData");

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 본문 설정
        Map<String, String> body = new HashMap<>();
        body.put("content", "오늘의 추천 도서의 제목만 알려줘");

        // HTTP 요청 Entity 생성
        HttpEntity<Map<String , String >> entity = new HttpEntity<>(body, headers);

        // REST API 호출
        ResponseEntity<Map> response = restTemplate.exchange(
                "http://3.38.1.41:5000/llm",    // 요청 URL
                HttpMethod.POST,                        // Http 메서드 타입
                entity,                                 // 요청 데이터
                Map.class                               // 응답(Return) 타입
        );

        log.info("entity : " + entity);
        log.info("response : " + response);

        return response;
    }

    @Override
    public ResponseEntity<Map> getPersonalLLMData(String regId) throws Exception {

        log.info("service getPersonalLLMData");

        // 현재 내 선호 장르 가져오기
        List<PreferEntity> entities = preferRepository.findByRegId(regId);

        // 선호 장르들을 콤마(,)로 연결된 문자열로 초기화
        String content = entities.stream()
                .map(preferEntity -> preferEntity.getCategory())
                .collect(Collectors.joining(", "));

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 본문 설정
        Map<String, String> body = new HashMap<>();
        body.put("content", content + "중 하나의 장르에서 도서 한권의 제목만 알려줘");

        // HTTP 요청 Entity 생성
        HttpEntity<Map<String , String >> entity = new HttpEntity<>(body, headers);

        // REST API 호출
        ResponseEntity<Map> response = restTemplate.exchange(
                "http://49.50.172.130:5000/llm",    // 요청 URL
                HttpMethod.POST,                        // Http 메서드 타입
                entity,                                 // 요청 데이터
                Map.class                               // 응답(Return) 타입
        );


        log.info("entity : " + entity);
        log.info("response : " + response);

        return response;
    }
}
