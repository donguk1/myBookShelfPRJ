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

    private final RestTemplate restTemplate = new RestTemplate();
    private final PreferRepository preferRepository;

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

    @Override
    public ResponseEntity<Map> getPersonalLLMData(String regId) throws Exception {

        log.info("service getPersonalLLMData");

        List<PreferEntity> entities = preferRepository.findByRegId(regId);

        String content = entities.stream()
                .map(preferEntity -> preferEntity.getCategory())
                .collect(Collectors.joining(", "));


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("content", content + "중 하나의 장르에서 도서 한권의 제목만 알려줘");

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
