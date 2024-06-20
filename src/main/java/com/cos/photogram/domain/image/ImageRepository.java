package com.cos.photogram.domain.image;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :fromUserId) ORDER BY id DESC", nativeQuery = true)
    List<Image> selectImageList(Integer fromUserId, Pageable pageable);

    // 인기 페이지 쿼리
    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT *, COUNT(*) likeCount FROM likes GROUP BY imageId) v " +
            "ON i.id = v.imageId ORDER BY v.likeCount DESC, i.id DESC", nativeQuery = true)
    List<Image> selectPopular();
}
