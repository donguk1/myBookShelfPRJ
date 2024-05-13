package kopo.poly.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ILLMService {

    ResponseEntity<Map> getLLMData(String content) throws Exception;
;
}
