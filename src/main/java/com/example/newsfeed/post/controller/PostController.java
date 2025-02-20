package com.example.newsfeed.post.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.post.dto.PostRequest;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Response<PostResponse>> createPost(
            @Valid @RequestBody PostRequest dto, @Login LoginUser loginUser) {
        return new ResponseEntity<>(Response.of(postService.save(loginUser.getUserId(), dto.getTitle(), dto.getContents(), dto.getKeywords())), HttpStatus.CREATED);
    }

    // findAll (default : 최신순)
    @GetMapping
    public ResponseEntity<Response<PostShortResponse>> findAllPosts(@Valid @ModelAttribute PageRequest page) {
        return new ResponseEntity<>(Response.fromPage(postService.findAllPosts(page.getSize(), page.getPage())), HttpStatus.OK);
    }

    // findAll (좋아요순)
    @GetMapping("/like")
    public ResponseEntity<Response<PostShortResponse>> findAllWithLikeSorted(@Valid @ModelAttribute PageRequest page) {
        return new ResponseEntity<>(Response.fromPage(postService.findAllWithLikeSorted(page.getSize(), page.getPage())), HttpStatus.OK);
    }

    //findOne
    @GetMapping("/{id}")
    public ResponseEntity<Response<PostResponse>> findPostById(@PathVariable Long id) {
        return new ResponseEntity<>(Response.of(postService.findPostById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<PostResponse>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest dto,
            @Login LoginUser loginUser) {
        return new ResponseEntity<>(Response.of(postService.update(loginUser.getUserId(), id, dto.getTitle(), dto.getContents(), dto.getKeywords())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @Login LoginUser loginUser) {
        postService.delete(loginUser.getUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
