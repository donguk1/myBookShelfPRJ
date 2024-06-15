package kopo.poly.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@NoArgsConstructor
@EnableRedisHttpSession()
@Configuration
public class RedisConfig {

    @Value(value = "${spring.data.redis.host}")
    private String redisHost;

    @Value(value = "${spring.data.redis.port}")
    private int redisPort;

    @Value(value = "${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);        // 호스트 설정
        redisStandaloneConfiguration.setPort(redisPort);            // 포트 설정
        redisStandaloneConfiguration.setPassword(redisPassword);    // 패스워드 설정
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());                // 키 시리얼라이저 설정
        stringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 값 시리얼라이저 설정
        stringRedisTemplate.setDefaultSerializer(new StringRedisSerializer());            // 기본 시리얼라이저 설정
        stringRedisTemplate.afterPropertiesSet();                                         // 설정 적용
        return stringRedisTemplate;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
