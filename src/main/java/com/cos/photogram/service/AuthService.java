package com.cos.photogram.service;


import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional // 트랜잭션 관리 애노테이션
    public User signUpService(User user) {
        log.info(" ##### run signUpService :: {} ", user.toString());

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // user : 외부에서 클라이언트를 통해 생성된 객체
        // saveUser : DB 저장 후 응답 받은 객체
        User saveUser = userRepository.save(user);

        return saveUser;
    }
}
