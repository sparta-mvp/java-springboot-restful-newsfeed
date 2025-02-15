package com.example.newsfeed.comment.service;

import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //TODO: page size 결정
    private final static int PAGE_SIZE = 20;


    public CommentResponseDto addComment(Long postId, User login, String contents) {

        //TODO: user 정보, post 정보 가져오기

        Comment save = commentRepository.save(new Comment(user, post, contents));
        return CommentResponseDto.toDto(save);
    }

    public List<CommentResponseDto> findByPostIdToComments(Long postId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Comment> commentList = commentRepository.findByPostId(postId, pageable);

        return commentList.stream().map(CommentResponseDto::toDto).toList();
    }


    //TODO: update/delete -> session-댓글 작성자 확인 필요
    @Transactional
    public CommentResponseDto updateComment(Long id, User writer, String contents) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);
        Comment saveComment = findComment.update(contents);
        return CommentResponseDto.toDto(saveComment);
    }

    public void deleteComment(Long id, User writer) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        commentRepository.delete(comment);
    }
}
