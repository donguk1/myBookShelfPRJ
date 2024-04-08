package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class MyBookShelfPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShelfPrjApplication.class, args);
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        // 다른 구성 옵션을 설정할 수 있음
//        return resolver;
//    }

}
