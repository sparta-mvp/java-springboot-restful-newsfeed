package com.example.newsfeed.comment.service.component;

import com.example.newsfeed.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReader {

    private final CommentRepository commentRepository;


}
