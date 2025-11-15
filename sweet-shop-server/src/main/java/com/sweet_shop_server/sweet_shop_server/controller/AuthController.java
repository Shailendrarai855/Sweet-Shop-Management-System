package com.sweet_shop_server.sweet_shop_server.controller;

import com.sweet_shop_server.sweet_shop_server.dto.LoginRequest;
import com.sweet_shop_server.sweet_shop_server.dto.LoginResponse;
import com.sweet_shop_server.sweet_shop_server.dto.UserDTO;
import com.sweet_shop_server.sweet_shop_server.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
//    private final LoginRequest loginRequest;


    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO){
        return authService.register(userDTO);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
                               HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        LoginResponse loginResponse = authService.login(loginRequest);

        Cookie cookie = new Cookie("token", loginResponse.getRefreshToken());
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

        return loginResponse;
    }

}
