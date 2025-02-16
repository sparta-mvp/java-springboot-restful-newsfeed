package com.example.newsfeed.user.dto;

import com.example.newsfeed.common.validator.EnumValid;
import com.example.newsfeed.user.entity.InterestTag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank(message = "이름은 널일 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 널일 수 없습니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 널일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
            message = "비밀번호는 영문/숫자/특수문자 최소 1개씩 포함, 8~16자로 설정해주세요. 적어도 하나의 특수 문자 (물결표(~), 느낌표(!), @, #, $, %, ^, &, *, (, ), +, | 등)가 포함되어야 합니다.")
    private String password;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
            message = "비밀번호 확인은 영문/숫자/특수문자 최소 1개씩 포함, 8~16자로 설정해주세요. 적어도 하나의 특수 문자 (물결표(~), 느낌표(!), @, #, $, %, ^, &, *, (, ), +, | 등)가 포함되어야 합니다.")
    @NotBlank(message = "비밀번호확인은 널일 수 없습니다.")
    private String passwordCheck;

    @EnumValid(enummClass = InterestTag.class, message = "유효한 태그를 입력하세요")
    @NotBlank(message = "태그는 널일 수 없습니다.")
    private String interestTag;


}
