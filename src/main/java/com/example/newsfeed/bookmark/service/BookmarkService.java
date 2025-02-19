package com.example.newsfeed.bookmark.service;

import com.example.newsfeed.bookmark.dto.BookmarkResponse;
import com.example.newsfeed.bookmark.entity.Bookmark;
import com.example.newsfeed.bookmark.repository.BookmarkRepository;
import com.example.newsfeed.bookmark.service.component.BookmarkFinder;
import com.example.newsfeed.bookmark.service.component.BookmarkReader;
import com.example.newsfeed.bookmark.service.component.BookmarkWriter;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkFinder bookmarkFinder;
    private final BookmarkReader bookmarkReader;
    private final BookmarkWriter bookmarkWriter;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 북마크 추가 (로그인한 유저가 다른 유저의 게시글을 저장)
     */
    @Transactional
    public BookmarkResponse addBookmark(Long userId, Long postId) {
        // 🔹 유저 & 게시글 존재 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 🔹 중복 북마크 체크
        if (bookmarkFinder.findBookmark(user, post) != null) {
            throw new IllegalArgumentException("이미 북마크한 게시글입니다.");
        }

        // 🔹 북마크 저장
        Bookmark bookmark = new Bookmark(user, post);
        bookmarkWriter.saveBookmark(bookmark);

        return new BookmarkResponse(bookmark.getId(),bookmark.getUser().getId(),bookmark.getPost().getId());
    }

    /**
     * 북마크 삭제 (로그인한 유저가 저장한 북마크 제거)
     */
    @Transactional
    public void removeBookmark(Long userId, Long postId) {
        // 🔹 유저 & 게시글 존재 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 🔹 북마크 조회 (없으면 예외 발생)
        Bookmark bookmark = bookmarkFinder.findBookmark(user, post);
        bookmarkWriter.deleteBookmark(bookmark.getId());
    }

    //북마크 단건 조회
    @Transactional(readOnly = true)
    public BookmarkResponse getBookmarkById(Long bookmarkId, Long userId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndId(bookmarkId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Bookmark not found"));

        return new BookmarkResponse(bookmark.getId(), bookmark.getUser().getId(), bookmark.getPost().getId());
    }

    //북마크 목록 조회
    @Transactional(readOnly = true)
    public List<BookmarkResponse> getBookmarks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(userId);
        List<BookmarkResponse> dtos = new ArrayList<>();

        return bookmarkList.stream()
                .map(bookmark -> new BookmarkResponse(
                        bookmark.getId(),
                        bookmark.getUser().getId(),
                        bookmark.getPost().getId()
                ))
                .toList();
    }
}
