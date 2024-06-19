package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.service.IFileService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/file")
@RestController
public class FileController {

    private final IFileService fileService;

    @DeleteMapping(value = "/deleteImage")
    public void deleteImage(HttpServletRequest request) throws Exception {

        log.info("controller deleteImage");

        Long fileSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("fileSeq")));

        log.info("fileSeq : " + fileSeq);

        fileService.deleteImage(fileSeq);
    }
}
