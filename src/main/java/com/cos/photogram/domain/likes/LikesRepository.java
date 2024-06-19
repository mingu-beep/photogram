package com.cos.photogram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Modifying
    @Query(value = "INSERT INTO likes(imageId, userId, createDate) VALUES (:imageId, :userId, now())", nativeQuery = true)
    void insertLikes(int imageId, int userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE (imageId = :imageId AND userId = :userId)", nativeQuery = true)
    void deleteLikes(int imageId, int userId);
}
