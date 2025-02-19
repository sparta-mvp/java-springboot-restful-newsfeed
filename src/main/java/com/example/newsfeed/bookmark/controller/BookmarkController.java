package com.example.newsfeed.bookmark.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.bookmark.dto.BookmarkRequest;
import com.example.newsfeed.bookmark.dto.BookmarkResponse;
import com.example.newsfeed.bookmark.service.BookmarkService;
import com.example.newsfeed.common.resolvers.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

    public void test(){}

    private final BookmarkService bookmarkService;

    //북마크 추가 (로그인한 유저가 다른 유저의 게시글을 저장)
    @PostMapping
    public ResponseEntity<BookmarkResponse> addBookmark(@RequestBody BookmarkRequest bookmarkRequest, @Login LoginUser loginUser) {
        BookmarkResponse response = bookmarkService.addBookmark(loginUser.getUserId(),bookmarkRequest.getPostId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //북마크 삭제 (로그인한 유저가 저장한 북마크 제거)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeBookmark(@Login LoginUser loginUser, @PathVariable Long postId) {
        bookmarkService.removeBookmark(loginUser.getUserId(), postId);
        return ResponseEntity.noContent().build();
    }

    //로그인한 유저의 북마크 목록 조회
    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> getUserBookmarks(@Login LoginUser loginUser) {
        List<BookmarkResponse> bookmarks = bookmarkService.getBookmarks(loginUser.getUserId());
        return ResponseEntity.ok(bookmarks);
    }

    //로그인한 유저의 북마크 단건 조회
    @GetMapping("/{bookmarkId}")
    public ResponseEntity<BookmarkResponse> getBookmarkById(@PathVariable Long bookmarkId ,@Login LoginUser loginUser) {
            BookmarkResponse bookmark = bookmarkService.getBookmarkById(bookmarkId, loginUser.getUserId());
        return ResponseEntity.ok(bookmark);
    }
}
