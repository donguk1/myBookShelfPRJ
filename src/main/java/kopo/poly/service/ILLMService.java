package kopo.poly.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ILLMService {

    ResponseEntity<Map> getLLMData() throws Exception;
    ResponseEntity<Map> getPersonalLLMData(String regId) throws Exception;


}
