package kopo.poly.service.impl;


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
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class LLMService implements ILLMService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<Map> getLLMData() throws Exception {

        log.info(this.getClass().getName() + " getLLMData");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("content", "오늘의 추천 도서의 제목만 알려줘");

        HttpEntity<Map<String , String >> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "http://49.50.172.130:5000/llm",
                HttpMethod.POST,
                entity,
                Map.class
        );

        log.info("entity : " + entity);
        log.info("response : " + response);

        return response;
    }


}
