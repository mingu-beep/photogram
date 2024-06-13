package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User updateUserInfo(Integer id, User user) {

        // 1. 영속화
        User userEntity = userRepository.findById(id).get();

        // 2. 영속화된 오브젝트 수정
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setTel(user.getTel());
        userEntity.setGender(user.getGender());

        // 3. 리턴 성공 시 더티체킹 진행되면서 업데이트 완료
        return userEntity;
    }
}
