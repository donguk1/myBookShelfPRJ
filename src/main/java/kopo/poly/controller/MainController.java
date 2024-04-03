package kopo.poly.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {


    @GetMapping(value = "main")
    public String main() {

        log.info("메인 페이지 이동");

        return "main";
    }
}
