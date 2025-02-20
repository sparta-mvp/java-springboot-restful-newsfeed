package com.example.newsfeed.commentlike.repository;

import com.example.newsfeed.commentlike.dto.CommentLikeCountStatusDto;
import com.example.newsfeed.commentlike.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {


    @Query("select CASE WHEN count(cl) > 0 then true else false end from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId")
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    @Query("select cl from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId")
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);

    Long countByCommentId(Long commentId);

    @Query("select new com.example.newsfeed.commentlike.dto.CommentLikeCountStatusDto(" +
            "cl.comment.id ," +
            " count(cl) as ," +
            "case when count(case when cl.user.id = :userId then 1 else 0 end) > 0 then true else false end)" +
            " from CommentLike cl " +
            " where cl.comment.id in :commentIds " +
            "group by cl.comment.id"
    )
    List<CommentLikeCountStatusDto> findCommentsLikeCountAndStatus(List<Long> commentIds, Long userId);

    @Query("select new com.example.newsfeed.commentlike.dto.CommentLikeCountStatusDto(" +
            "cl.comment.id ," +
            " count(cl) )" +
            "from CommentLike cl " +
            "where cl.comment.id in :commentIds " +
            "group by cl.comment.id"
    )
    List<CommentLikeCountStatusDto> findCommentsLikeCount(List<Long> commentIds);


}
