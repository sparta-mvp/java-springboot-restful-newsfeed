package com.example.newsfeed.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private Enum[] enumValues;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.enumValues = constraintAnnotation.enummClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        return Arrays.stream(enumValues).anyMatch(anEnum -> anEnum.name().equals(value));
    }
}
