package com.example.newsfeed.post.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.post.dto.PostLoggedResponse;
import com.example.newsfeed.post.dto.PostRequest;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        PostResponse saved = postService.save(loginUser.getUserId(), dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // findAll (default : 최신순)
    @GetMapping
    public ResponseEntity<Page<PostShortResponse>> findAllPosts(@Valid @ModelAttribute PageRequest page) {
        Page<PostShortResponse> postsList = postService.findAllPosts(page.getSize(), page.getPage());
        return new ResponseEntity<>(postsList, HttpStatus.OK);
    }

    // findAll (좋아요순)
    @GetMapping("/like")
    public ResponseEntity<Page<PostShortResponse>> findAllWithLikeSorted(@Valid @ModelAttribute PageRequest page) {
        Page<PostShortResponse> postsList = postService.findAllWithLikeSorted(page.getSize(), page.getPage());
        return new ResponseEntity<>(postsList, HttpStatus.OK);
    }

    //findOne
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse post = postService.findPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest dto,
            @Login LoginUser loginUser) {
        PostResponse updated = postService.update(loginUser.getUserId(), id, dto.getTitle(), dto.getContents(), dto.getKeywords());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @Login LoginUser loginUser) {
        postService.delete(loginUser.getUserId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
