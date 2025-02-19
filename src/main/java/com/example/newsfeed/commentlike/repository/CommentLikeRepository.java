package com.example.newsfeed.commentlike.repository;

import com.example.newsfeed.commentlike.entity.CommentLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CommentLikeRepository {

    private final EntityManager em;

    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        boolean present = em.createQuery("select cl from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId")
                .setParameter("commentId", commentId)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .isPresent();
        return present;

    }

    public void save(CommentLike commentLike) {
        em.persist(commentLike);
    }

    public long countByCommentId(Long commentId) {
        return em.createQuery("select count(cl) from CommentLike cl where cl.comment.id = :commentId", Long.class)
                .setParameter("commentId",commentId)
                .getSingleResult();
    }

    public Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId) {
        return em.createQuery("select cl from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId", CommentLike.class)
                .setParameter("commentId", commentId)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst();
    }

    public void delete(CommentLike commentLike) {
        em.remove(commentLike);
    }
}
