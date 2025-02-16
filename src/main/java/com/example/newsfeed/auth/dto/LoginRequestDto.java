package com.example.newsfeed.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
