package kopo.poly.controller;

import kopo.poly.service.IBookShelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "bookShelf")
@RestController
public class BookShelfController {

    private final IBookShelfService bookShelfService;


}
