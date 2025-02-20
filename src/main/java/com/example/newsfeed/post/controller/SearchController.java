package com.example.newsfeed.post.controller;

import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.entity.SearchType;
import static com.example.newsfeed.post.entity.SearchType.*;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/search")
public class SearchController {
    private final PostService postService;

    // 구분 X
    @GetMapping
    public ResponseEntity<Response<PostShortResponse>> searchPosts(
            @Valid @ModelAttribute PageRequest page,
            @RequestParam(required = false) String all,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String contents,
            @RequestParam(required = false) String keywords
    ) {
        SearchType searchType = ALL;
        String query = null;

        if (all != null) {
            query = all;
        } else if (title != null) {
            searchType = TITLE;
            query = title;
        } else if (contents != null) {
            searchType = CONTENTS;
            query = contents;
        } else if (keywords != null) {
            searchType = KEYWORDS;
            query = keywords;
        }

        if (query == null) {
            Page<PostShortResponse> postsList = postService.findAllPosts(page.getSize(), page.getPage());
            return new ResponseEntity<>(Response.fromPage(postsList), HttpStatus.OK);
        }

        Page<PostShortResponse> postsLists = postService.findWithQuery(searchType, query, page.getSize(), page.getPage());
        return new ResponseEntity<>(Response.fromPage(postsLists), HttpStatus.OK);
    }

}
