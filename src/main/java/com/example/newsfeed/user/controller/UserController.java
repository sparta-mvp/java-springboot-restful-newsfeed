package com.example.newsfeed.user.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.user.dto.PasswordUpdateRequestDto;
import com.example.newsfeed.user.dto.SignupRequestDto;
import com.example.newsfeed.user.dto.UserResponseDto;
import com.example.newsfeed.user.dto.UserUpdateRequestDto;
import com.example.newsfeed.user.dto.WithdrawRequestDto;
import com.example.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("/withdraw")
    public ResponseEntity<MessageResponse> withdraw(@Login LoginUser loginUser, @RequestBody @Valid WithdrawRequestDto requestDto) {
        userService.withdraw(loginUser, requestDto.getPassword());
        return ResponseEntity.ok(MessageResponse.of("회원탈퇴 성공했습니다."));
    }

    @PatchMapping
    public ResponseEntity<MessageResponse> updateUser(@Login LoginUser loginUser, @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(loginUser, requestDto);
        return ResponseEntity.ok(MessageResponse.of("회원 정보가 수정되었습니다."));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> updatePassword(@Login LoginUser loginUser, @RequestBody @Valid PasswordUpdateRequestDto requestDto) {
        userService.updatePassword(loginUser, requestDto);

        return ResponseEntity.ok(MessageResponse.of("비밀번호 변경 성공했습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserResponseDto>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(Response.of(userService.getUserById(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<Response<UserResponseDto>> getProfile(@Login LoginUser loginUser) {
        return ResponseEntity.ok(Response.of(userService.getUserById(loginUser.getUserId())));
    }
}
