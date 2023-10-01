package com.springproject.sns.repository;

import com.springproject.sns.model.entity.CommentEntity;
import com.springproject.sns.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CommentEntity entity SET entity.removedAt = NOW() where entity.post = :post", nativeQuery = true)
    void deleteAllByPost(@Param("post") PostEntity post);
}
