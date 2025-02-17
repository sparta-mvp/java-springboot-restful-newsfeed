package com.example.newsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {

    @NotBlank(message = "현재 비밀번호를 입력해야 합니다.")
    private String oldPassword;
    @NotBlank(message = "새 비밀번호를 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
            message = "비밀번호는 영문/숫자/특수문자 최소 1개씩 포함, 8~16자로 설정해주세요. 적어도 하나의 특수 문자 (물결표(~), 느낌표(!), @, #, $, %, ^, &, *, (, ), +, | 등)가 포함되어야 합니다.")
    private String newPassword;
    @NotBlank(message = "새 비밀번호 확인을 입력해야 합니다.")
    private String newPasswordCheck;
}
