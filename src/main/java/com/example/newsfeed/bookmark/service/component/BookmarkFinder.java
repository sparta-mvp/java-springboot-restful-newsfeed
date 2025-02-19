package com.example.newsfeed.bookmark.service.component;

import com.example.newsfeed.bookmark.entity.Bookmark;
import com.example.newsfeed.bookmark.repository.BookmarkRepository;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkFinder {
    private final BookmarkRepository bookmarkRepository;

    public Bookmark findBookmark(User user, Post post) {
        return bookmarkRepository.findByUserAndPost(user, post);
    }
}
