package com.example.newsfeed.comment.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.dto.CommentDetailResponse;
import com.example.newsfeed.comment.dto.CommentResponse;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.InvalidCommentUserException;
import com.example.newsfeed.comment.service.component.CommentFinder;
import com.example.newsfeed.comment.service.component.CommentWriter;
import com.example.newsfeed.commentlike.dto.CommentLikeCountStatusDto;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.exception.PostNotFoundException;
import com.example.newsfeed.post.service.component.PostFinder;
import com.example.newsfeed.post.service.component.PostReader;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFinder commentFinder;
    private final CommentWriter commentWriter;

    private final UserFinder userFinder;

    private final PostFinder postFinder;
    private final PostReader postReader;


    private final CommentLikeRepository commentLikeRepository;

    public CommentResponse addComment(Long postId, LoginUser loginUser, String contents) {

        User user = userFinder.findActive(loginUser.getUserId());
        Post post = postFinder.findPost(postId);

        Comment save = commentWriter.saveComment(new Comment(user, post, contents));
        return CommentResponse.from(save);
    }


    @Transactional(readOnly = true)
    public Page<CommentDetailResponse> findByPostIdToComments(LoginUser loginUser, Long postId, int pageSize, int pageNumber) {
        if (!postReader.doesExist(postId)) {
            throw new PostNotFoundException();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //N+1문제로 새 메서드 작성
//        Page<Comment> commentList = commentFinder.getCommentsByPost(postId, pageable);

        Page<Comment> commentList = commentFinder.findCommentsWithPostAndUserByPostId(postId, pageable);

        List<Long> commentIds = commentList.stream().map(comment -> comment.getId()).toList();

        List<CommentLikeCountStatusDto> commentLikeCountStatusDtos = getCommentLikeData(loginUser, commentIds);

        Map<Long, CommentLikeCountStatusDto> commentLikeCountStatusDtoMap = commentLikeCountStatusDtos.stream().collect(Collectors.toMap(commentLikeCountStatusDto -> commentLikeCountStatusDto.getCommentId(), commentLikeCountStatusDto -> commentLikeCountStatusDto));

        return commentList.map(comment -> CommentDetailResponse.of(
                comment.getUser().getName(),
                comment.getPost().getTitle(),
                comment.getContents(),
                commentLikeCountStatusDtoMap.get(comment.getId()) == null ? 0L : commentLikeCountStatusDtoMap.get(comment.getId()).getCount(),
                commentLikeCountStatusDtoMap.get(comment.getId()) == null ? false : commentLikeCountStatusDtoMap.get(comment.getId()).isLiked(),
                comment.getUpdatedAt()
        ));
    }


    @Transactional
    public CommentResponse updateComment(Long id, LoginUser loginUser, String contents) {
        Comment findComment = commentFinder.getComment(id);

        if (!findComment.getUser().isSame(loginUser.getUserId())) {
            throw new InvalidCommentUserException();
        }

        Comment saveComment = findComment.update(contents);
        return CommentResponse.from(saveComment);
    }

    @Transactional
    public void deleteComment(Long id, LoginUser loginUser) {
        Comment findComment = commentFinder.getComment(id);
        if (!findComment.getUser().isSame(loginUser.getUserId())) {
            throw new InvalidCommentUserException();
        }
        commentWriter.deleteComment(id);
    }

    private List<CommentLikeCountStatusDto> getCommentLikeData(LoginUser loginUser, List<Long> commentIds) {
        if (loginUser != null) {
            return commentLikeRepository.findCommentsLikeCountAndStatus(commentIds, loginUser.getUserId());
        }
        return commentLikeRepository.findCommentsLikeCount(commentIds);
    }


}
