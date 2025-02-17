package com.example.newsfeed.user.controller;

import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.user.dto.SignupRequest;
import com.example.newsfeed.user.dto.UserResponse;
import com.example.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public MessageResponse signup(@RequestBody @Valid SignupRequest requestDto) {

        userService.signup(requestDto);
        return MessageResponse.of("회원가입이 성공하였습니다.");
    }

    @GetMapping("/{id}")
    public Response<UserResponse> getUser(@PathVariable Long id) {
        return Response.of(userService.getUser(id));
    }


}
