package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.ShoppingDTO;
import kopo.poly.service.ILLMService;
import kopo.poly.service.INaverService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "recommend")
@Controller
public class recommendController {

    private final ILLMService llmService;
    private final INaverService naverService;


    @GetMapping(value = "bookRecommend")
    public String bookRecommend() {
        return "recommend/bookRecommend";
    }

    @ResponseBody
    @PostMapping(value = "getRecommendBook")
    public MsgDTO getRecommendBook() throws Exception {

        log.info("controller getRecommendBook");

        ResponseEntity<Map> response = llmService.getLLMData();

        return MsgDTO.builder()
                .msg((String) response.getBody().get("result"))
                .build();
    }

    @ResponseBody
    @PostMapping(value = "getShoppingList")
    public ShoppingDTO getShoppingList(HttpServletRequest request) throws Exception {

        log.info("controller getShoppingList");

        String title = CmmUtil.nvl(request.getParameter("bookTitle"));

        return naverService.getShoppingList(title);
    }
}
