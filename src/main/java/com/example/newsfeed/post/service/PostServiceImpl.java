package com.example.newsfeed.post.service;

import com.example.newsfeed.bookmark.service.component.BookmarkWriter;
import com.example.newsfeed.comment.service.component.CommentWriter;
import static com.example.newsfeed.common.exception.ErrorCode.*;
import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.entity.SearchType;
import com.example.newsfeed.post.service.component.PostFinder;
import com.example.newsfeed.post.service.component.PostReader;
import com.example.newsfeed.post.service.component.PostWriter;
import com.example.newsfeed.post_like.service.component.PostLikeReader;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostReader postReader;
    private final PostWriter postWriter;
    private final PostFinder postFinder;

    private final UserFinder userFinder;
    private final PostLikeReader postLikeReader;

    private final CommentWriter commentWriter;
    private final BookmarkWriter bookmarkWriter;

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

        return new PostResponse(post.getTitle(), post.getContents(), post.getUser().getName(),
                post.getKeyword(), post.getCreatedAt(), post.getUpdatedAt(), 0);
    }

    @Override
    @Transactional
    public Page<PostShortResponse> findAllPosts(int pageSize, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postFinder.findAll(pageable);
    }

    // 좋아요순
    @Override
    @Transactional
    public Page<PostShortResponse> findAllWithLikeSorted(int pageSize, int pageNumber) {
        if (pageSize < 1) throw new ValidationException(PAGE_NOT_POSITIVE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postFinder.findAllWithLikeSorted(pageable);
    }

    @Override
    @Transactional
    public PostResponse findPostById(Long id) {
        if (!postReader.doesExist(id)) {
            throw new ValidationException(POST_NOT_FOUND);
        }

        Post post = postFinder.findPost(id);
        long likeCnt = postLikeReader.likeCountOfPost(post);

        return new PostResponse(post, likeCnt);
    }

    @Override
    @Transactional
    public PostResponse update(Long userId, Long id, String title, String contents, String keyword) {
        if (!postReader.doesExist(id)) throw new ValidationException(POST_NOT_FOUND);

        if (title == null || contents == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Post post = postFinder.findPost(id);
        User writer = userFinder.findActive(userId);

        if (!post.getUser().equals(writer)) throw new ValidationException(UNAUTHORIZED_ACCESS);

        post.updatePost(title, contents, keyword);

        long likeCnt = postLikeReader.likeCountOfPost(post);

        return new PostResponse(post, likeCnt);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        Post post = postFinder.findPost(id);

        if (!post.getUser().equals(userFinder.findActive(userId))) {
            throw new ValidationException(UNAUTHORIZED_ACCESS);
        }

        //TODO 연관 댓글 삭제, 북마크 삭제
        commentWriter.bulkDeleteByPostId(post.getId());
        bookmarkWriter.bulkDeleteByPostId(post.getId());
        postWriter.deletePost(post);
    }

    @Override
    public Page<PostShortResponse> findWithQuery(SearchType searchType, String query, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Post> postPage = null;

        if (searchType.equals(SearchType.ALL)) {
            postPage = postFinder.searchPosts(pageable, query);
        } else if (searchType.equals(SearchType.TITLE)) {
            postPage = postFinder.searchPostsWithTitle(pageable, query);
        } else if (searchType.equals(SearchType.CONTENTS)) {
            postPage = postFinder.searchPostsWithContents(pageable, query);
        } else if (searchType.equals(SearchType.KEYWORDS)) {
            postPage = postFinder.searchPostsWithKeyWords(pageable, query);
        }

        return postPage.map(post ->
                new PostShortResponse(
                        post.getTitle(),
                        post.getContents(),
                        post.getUser().getName(),
                        post.getKeyword(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        postLikeReader.likeCountOfPost(post)
                ));
    }
}
