package com.example.newsfeed.bookmark.service.component;

import com.example.newsfeed.bookmark.entity.Bookmark;
import com.example.newsfeed.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkWriter {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void saveBookmark(Bookmark bookmark) { bookmarkRepository.save(bookmark);}

    @Transactional
    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    public void bulkDeleteByPostId(Long postId) {
        bookmarkRepository.deleteByPostId(postId);
    }
}
