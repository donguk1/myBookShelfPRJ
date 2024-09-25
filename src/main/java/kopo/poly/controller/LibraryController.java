package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.DataLibraryDTO;
import kopo.poly.service.IAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("library")
public class LibraryController {

    private final IAPIService apiService;


    @PostMapping(value = "getLibraryItem")
    public List<DataLibraryDTO> getLibraryItem(HttpServletRequest request) throws Exception{

        log.info("controller getLibraryItem");

        String title = request.getParameter("bookTitle");

        log.info("title: {}", title);

        return apiService.getDataLibraryList(title, "MB");
    }
}
