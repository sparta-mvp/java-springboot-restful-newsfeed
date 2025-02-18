package com.example.newsfeed.post.service;

import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.component.PostFinder;
import com.example.newsfeed.post.service.component.PostReader;
import com.example.newsfeed.post.service.component.PostWriter;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import static com.example.newsfeed.common.exception.ErrorCode.POST_NOT_FOUND;
import static com.example.newsfeed.common.exception.ErrorCode.UNAUTHORIZED_ACCESS;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostReader postReader;
    private final PostWriter postWriter;
    private final PostFinder postFinder;

    //private final UserReader userReader;
    private final UserFinder userFinder;

    @Override
    @Transactional
    public PostResponse save(Long userId, String title, String contents, String keywords) {

        User user = userFinder.findActive(userId);

        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Post post = new Post(title, contents, keywords);
        post.setUser(user);

        postWriter.createPost(post);

        return new PostResponse(post.getTitle(), post.getUser().getName(), post.getContents(),
                post.getKeyword(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Override
    @Transactional
    public Page<PostShortResponse> findAllPosts(int pageSize, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = postFinder.findPosts(pageable);

        return postPage.map(post ->
                new PostShortResponse(
                        post.getTitle().substring(0, Math.min(post.getTitle().length(), 10)),
                        post.getContents().substring(0, Math.min(post.getContents().length(), 100)),
                        post.getUser().getName(),
                        post.getKeyword(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ));
    }

    @Override
    @Transactional
    public PostResponse findPostById(Long id) {
        if(!postReader.doesExist(id)){
            throw new ValidationException(POST_NOT_FOUND);
        }
        return new PostResponse(postFinder.findPost(id));
    }

    @Override
    @Transactional
    public PostResponse update(Long userId, Long id, String title, String contents, String keyword) {
        if(!postReader.doesExist(id)) throw new ValidationException(POST_NOT_FOUND);

        if (title == null || contents == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Post post = postFinder.findPost(id);
        User writer = userFinder.findActive(userId);

        if(!post.getUser().equals(writer)) throw new ValidationException(UNAUTHORIZED_ACCESS);

        post.updatePost(title, contents, keyword);

        //postWriter.createPost(post);

        return new PostResponse(post);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        Post post = postFinder.findPost(id);

        if(!post.getUser().equals(userFinder.findActive(userId))) {
            throw new ValidationException(UNAUTHORIZED_ACCESS);
        }

        postWriter.deletePost(post);
    }
}
