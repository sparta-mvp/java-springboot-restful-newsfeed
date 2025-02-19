package com.example.newsfeed.post.service.component;

import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReader {
    private final PostRepository postRepository;

    public boolean doesExist(Long id){
        return postRepository.existsById(id);
    }

}
