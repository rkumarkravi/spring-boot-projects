package com.egjpa.rkproj.repos;

import com.egjpa.rkproj.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.post.id = ?1 order by c.createdAt")
    Page<Comment> findByPost_IdOrderByCreatedAtAsc(@NonNull Long id, Pageable pageable);
}