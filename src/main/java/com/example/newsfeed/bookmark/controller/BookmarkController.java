package com.example.newsfeed.bookmark.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.bookmark.dto.BookmarkRequest;
import com.example.newsfeed.bookmark.dto.BookmarkResponse;
import com.example.newsfeed.bookmark.service.BookmarkService;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    //북마크 추가 (로그인한 유저가 다른 유저의 게시글을 저장)
    @PostMapping
    public ResponseEntity<Response<BookmarkResponse>> addBookmark(@RequestBody BookmarkRequest bookmarkRequest, @Login LoginUser loginUser) {
        BookmarkResponse response = bookmarkService.addBookmark(loginUser.getUserId(), bookmarkRequest.getPostId());
        return new ResponseEntity<>(Response.of(response), HttpStatus.CREATED);
    }

    //북마크 삭제 (로그인한 유저가 저장한 북마크 제거)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeBookmark(@Login LoginUser loginUser, @PathVariable Long postId) {
        bookmarkService.removeBookmark(loginUser.getUserId(), postId);
        return ResponseEntity.noContent().build();
    }

    //로그인한 유저의 북마크 목록 조회
    @GetMapping
    public ResponseEntity<Response<BookmarkResponse>> getUserBookmarks(@Login LoginUser loginUser, @ModelAttribute @Valid PageRequest pageRequest) {
        return new ResponseEntity<>(Response.fromPage(bookmarkService.getBookmarks(loginUser.getUserId(), pageRequest.getPage(), pageRequest.getSize())), HttpStatus.OK);
    }

    //로그인한 유저의 북마크 단건 조회
    @GetMapping("/{bookmarkId}")
    public ResponseEntity<Response<BookmarkResponse>> getBookmarkById(@PathVariable Long bookmarkId, @Login LoginUser loginUser) {
        return ResponseEntity.ok(Response.of(bookmarkService.getBookmarkById(bookmarkId, loginUser.getUserId())));
    }
}
