package com.cos.photogram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES (:fromUserId, :toUserId, now())", nativeQuery = true)
    int insertSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    int deleteSubscribe(int fromUserId, int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :fromUserId", nativeQuery = true)
    int selectCountSubscribe(int fromUserId);

    @Query(value = "SELECT COUNT(*) > 0 FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    int selectWhetherSubscribe(int fromUserId, int toUserId);
}
