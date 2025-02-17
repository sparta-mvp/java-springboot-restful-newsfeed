package com.example.newsfeed.comment.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.InvalidCommentUserException;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserFinder userFinder;


    //TODO: page size 결정
    private final static int PAGE_SIZE = 20;

    public CommentResponseDto addComment(Long postId, LoginUser loginUser, String contents) {

        //TODO: post 정보 가져오기
        User user = userFinder.findActive(loginUser.getUserId());

        Comment save = commentRepository.save(new Comment(user, post, contents));
        return CommentResponseDto.toDto(save);
    }

    public Page<CommentResponseDto> findByPostIdToComments(Long postId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Comment> commentList = commentRepository.findByPostId(postId, pageable);

        return commentList.map(CommentResponseDto::toDto);
    }


    //TODO: update/delete -> session-댓글 작성자 확인 필요
    @Transactional
    public CommentResponseDto updateComment(Long id, LoginUser loginUser, String contents) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);
        isEqualToUser(findComment.getUser().getId(), loginUser.getUserId());
        Comment saveComment = findComment.update(contents);
        return CommentResponseDto.toDto(saveComment);
    }


    public void deleteComment(Long id, LoginUser loginUser) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        isEqualToUser(comment.getUser().getId(), loginUser.getUserId());
        commentRepository.delete(comment);
    }

    private void isEqualToUser(Long loginUserId, Long commentUserId){
        if(! loginUserId.equals(commentUserId)){
            throw new InvalidCommentUserException();
        }
    }
}
