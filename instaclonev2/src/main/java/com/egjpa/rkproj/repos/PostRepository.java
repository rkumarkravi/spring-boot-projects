package com.egjpa.rkproj.repos;

import com.egjpa.rkproj.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.creator.id = ?1 order by p.createdOn DESC")
    List<Post> findByUser_IdOrderByCreatedOnDesc(@NonNull Long id, Pageable pageable);

    @Query("select p from Post p order by p.createdOn DESC")
    Page<Post> findByOrderByCreatedOnDesc(Pageable pageable);
}