package kopo.poly.controller;

import kopo.poly.service.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "comment")
@RestController
public class CommentController {

    private final ICommentService commentService;


}
