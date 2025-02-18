package com.example.newsfeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequest {

    @Size(max = 500, message = "You cannot enter more than 200 characters.")
    @NotBlank(message = "Please enter the details.")
    private String contents;

}
