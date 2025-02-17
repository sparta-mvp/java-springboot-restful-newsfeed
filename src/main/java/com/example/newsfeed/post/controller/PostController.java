package com.example.newsfeed.post.controller;

import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("") == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("");
        PostResponseDto saved = postService.save(userId, dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("") == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("");
        PostResponseDto updated = postService.update(userId, id, dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("") == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("");
        postService.delete(userId, id);
    }
}
