package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableFeignClients
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
public class MyBookShelfPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShelfPrjApplication.class, args);
    }



}
