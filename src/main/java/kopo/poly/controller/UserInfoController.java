package kopo.poly.controller;


import kopo.poly.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class UserInfoController {

    private final IUserInfoService userInfoService;


}
