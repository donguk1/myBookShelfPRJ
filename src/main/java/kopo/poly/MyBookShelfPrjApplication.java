package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
public class MyBookShelfPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShelfPrjApplication.class, args);
    }



}
