package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.dto.LoginRequestDto;
import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.auth.service.AuthenticationService;
import com.example.newsfeed.common.constant.SessionConst;
import com.example.newsfeed.common.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MessageResponse> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletRequest request) {
        LoginUser loginUser = authenticationService.login(requestDto.getEmail(), requestDto.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return ResponseEntity.ok(MessageResponse.of("로그인 성공했습니다."));
    }
}
