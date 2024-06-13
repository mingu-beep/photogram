package com.cos.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 에 이미 @Repository 가 포함되어있으므로 추가하지 않아도 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

    // JPA Query method 활용
    User findByUsername(String username);
}
