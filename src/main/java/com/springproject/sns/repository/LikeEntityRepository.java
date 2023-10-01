package com.springproject.sns.repository;

import com.springproject.sns.model.entity.LikeEntity;
import com.springproject.sns.model.entity.PostEntity;
import com.springproject.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);


    @Query(value = "SELECT COUNT(entity.id) from LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post") PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);

    @Transactional
    @Modifying
    @Query(value = "UPDATE LikeEntity entity SET entity.removedAt = NOW() where entity.post = :post", nativeQuery = true)
    void deleteAllByPost(@Param("post") PostEntity post);
}
