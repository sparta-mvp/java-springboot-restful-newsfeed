package com.example.newsfeed.commentlike;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.commentlike.service.CommentLikeService;
import com.example.newsfeed.commentlike.dto.CommentLikeRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment-likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public Response<Void> likeComment(@Login LoginUser loginUser, @RequestBody CommentLikeRequest commentLikeRequest){
        commentLikeService.likeComment(commentLikeRequest.getCommentId(), loginUser);
        return Response.empty();
    }

    @DeleteMapping
    public Response<Void> unlikeComment(@Login LoginUser loginUser, @RequestParam Long commentId){
        commentLikeService.unLikeComment(commentId, loginUser);
        return Response.empty();
    }

}
