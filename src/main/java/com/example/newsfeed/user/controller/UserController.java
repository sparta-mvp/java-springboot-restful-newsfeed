package com.example.newsfeed.user.controller;

import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.user.dto.SignupRequestDto;
import com.example.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid SignupRequestDto requestDto) {

        userService.signup(requestDto);
        return new ResponseEntity<>(MessageResponse.of("회원가입이 성공하였습니다."), HttpStatus.CREATED);
    }



}
