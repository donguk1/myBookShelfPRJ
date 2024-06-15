package kopo.poly.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Jackson2ObjectMapperBuilder를 사용하여 ObjectMapper 객체를 생성하고 반환
        // JSON 형식의 ObjectMapper를 생성
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule()) // JavaTimeModule을 등록하여 Java 8 날짜와 시간 API를 지원
                .build();
    }
}
