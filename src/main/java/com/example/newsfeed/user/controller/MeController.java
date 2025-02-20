package com.example.newsfeed.user.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.user.dto.PasswordUpdateRequest;
import com.example.newsfeed.user.dto.UserRequest;
import com.example.newsfeed.user.dto.UserResponse;
import com.example.newsfeed.user.dto.WithdrawRequest;
import com.example.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/me")
@RequiredArgsConstructor
public class MeController {
    private final UserService userService;

    @GetMapping
    public Response<UserResponse> getProfile(@Login LoginUser loginUser) {
        return Response.of(userService.getUser(loginUser.getUserId()));
    }

    @PatchMapping
    public MessageResponse updateProfile(@Login LoginUser loginUser, @RequestBody UserRequest requestDto) {
        userService.updateUser(loginUser, requestDto);
        return MessageResponse.of("회원 정보가 수정되었습니다.");
    }

    @PatchMapping("/password")
    public MessageResponse updatePassword(@Login LoginUser loginUser, @RequestBody @Valid PasswordUpdateRequest requestDto) {
        userService.updatePassword(loginUser, requestDto);

        return MessageResponse.of("비밀번호 변경 성공했습니다.");
    }

    @PostMapping("/withdraw")
    public MessageResponse withdraw(@Login LoginUser loginUser, @RequestBody @Valid WithdrawRequest requestDto, HttpServletRequest request) {
        userService.withdraw(loginUser, requestDto.getPassword());
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return MessageResponse.of("회원탈퇴 성공했습니다.");
    }
}
