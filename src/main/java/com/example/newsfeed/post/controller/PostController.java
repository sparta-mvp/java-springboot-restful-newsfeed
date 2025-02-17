package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.PostRequest;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.service.PostService;
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
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest dto, @Login LoginUser loginUser) {
        PostResponse saved = postService.save(loginUser.getId(), dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest dto,
            @Login LoginUser loginUser) {
        PostResponse updated = postService.update(loginUser.getId(), id, dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @Login LoginUser loginUser) {
        postService.delete(loginUser.getId(), id);
    }
}
