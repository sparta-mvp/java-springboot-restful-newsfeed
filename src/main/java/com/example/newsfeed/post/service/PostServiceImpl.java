package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import com.example.newsfeed.user.service.component.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserReader userReader;
    private final UserFinder userFinder;
    @Override
    public PostResponse save(Long userId, String title, String contents, String keywords) {
        Post post = new Post(title, contents, keywords);

        User user = userFinder.findActive(userId);

        post.setUser(user);

        Post saved = postRepository.save(post);
        return new PostResponse(saved.getTitle(), saved.getUser().getName(), saved.getContents(),
                saved.getKeyword(), saved.getCreatedAt(), saved.getUpdatedAt());
    }

    @Override
    public PostResponse update(Long userId, Long id, String title, String contents, String keyword) {
        Post post = postRepository.findByIdOrElseThrow(id);

        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정권한이 없습니다.");
        }
        post.updatePost(title, contents, keyword);

        return new PostResponse(post.getTitle(), post.getContents(), post.getUser().getName(),
                post.getKeyword(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Override
    public void delete(Long userId, Long id) {
        Post post = postRepository.findByIdOrElseThrow(id);

        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
