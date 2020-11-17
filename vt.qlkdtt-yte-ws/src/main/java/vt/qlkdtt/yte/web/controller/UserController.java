package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.LoginService;
import vt.qlkdtt.yte.service.sdo.UserTokenSdo;

@Slf4j
@RestController
@RequestMapping(value = "user")
@Api(value = "User")
public class UserController {
    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;
}
