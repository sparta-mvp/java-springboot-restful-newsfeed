package com.example.newsfeed.bookmark.service.component;

import com.example.newsfeed.bookmark.entity.Bookmark;
import com.example.newsfeed.bookmark.repository.BookmarkRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkReader {
    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksByUser(User user) {
        return bookmarkRepository.findAll();
    }
}
