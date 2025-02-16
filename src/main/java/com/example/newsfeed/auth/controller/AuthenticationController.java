package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.dto.LoginRequest;
import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.auth.service.AuthenticationService;
import com.example.newsfeed.common.constant.SessionConst;
import com.example.newsfeed.common.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public MessageResponse login(@RequestBody @Valid LoginRequest requestDto, HttpServletRequest request) {
        LoginUser loginUser = authenticationService.login(requestDto.getEmail(), requestDto.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return MessageResponse.of("로그인 성공했습니다.");
    }

    @PostMapping("/logout")
    public MessageResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return MessageResponse.of("로그아웃 성공했습니다.");
    }
}
