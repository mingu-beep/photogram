package com.cos.photogram.service;

import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User updateUserInfo(Integer id, User user) {

        // 1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomApiValidationException("찾을 수 없는 ID 입니다.");
        });

        // 2. 영속화된 오브젝트 수정
        userEntity.setName(user.getName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setTel(user.getTel());
        userEntity.setGender(user.getGender());

        // 3. 리턴 성공 시 더티체킹 진행되면서 업데이트 완료
        return userEntity;
    }
}
