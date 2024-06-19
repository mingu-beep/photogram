package com.cos.photogram.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :fromUserId)", nativeQuery = true)
    public List<Image> selectImageList(Integer fromUserId);
}
