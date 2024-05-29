package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.PreferDTO;
import kopo.poly.service.IPreferService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "prefer")
@RestController
public class PreferController {

    private final IPreferService preferService;

    @PostMapping(value = "updatePrefer")
    public void updatePrefer(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller updatePrefer");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String[] categoriesArray = request.getParameterValues("category");
        List<String> categories = categoriesArray != null ? Arrays.asList(categoriesArray) : new ArrayList<>();

        preferService.updatePrefer(regId, categories);
    }

    @PostMapping(value = "getPreferList")
    public List<PreferDTO> getPreferList(HttpSession session) throws Exception {

        log.info("controller getPreferList");

        String regId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        return preferService.getPreferList(regId);
    }
}
