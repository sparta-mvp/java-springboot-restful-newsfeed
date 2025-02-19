package com.example.newsfeed.post.service.component;

import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.exception.PostNotFoundException;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFinder {
    private final PostRepository postRepository;

    @Transactional
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException());
    }

//    public Page<Post> findPosts(Pageable pageable) {
//        return postRepository.findAll(pageable);
//    }

    public Page<PostShortResponse> findAll (Pageable pageable) {
        return postRepository.findAllWithLikeCnt(pageable);
    }

    public Page<PostShortResponse> findAllWithLikeSorted(Pageable pageable) {
        // 전체 포스트를 가져와 likeCnt로 desc
        return postRepository.findAllWithLikeSorted(pageable);
    }

    public Page<Post> searchPosts(Pageable pageable, String query) {
        return postRepository.searchByAllFields(query, pageable);
    }

    public Page<Post> searchPostsWithTitle(Pageable pageable, String query) {
        return postRepository.findByTitleContaining(query, pageable);
    }

    public Page<Post> searchPostsWithContents(Pageable pageable, String query) {
        return postRepository.findByContentsContaining(query, pageable);
    }

    public  Page<Post> searchPostsWithKeyWords(Pageable pageable, String query) {
        return postRepository.findByKeywordContaining(query, pageable);
    }
}
