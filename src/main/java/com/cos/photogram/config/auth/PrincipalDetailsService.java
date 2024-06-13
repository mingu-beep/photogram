package com.cos.photogram.config.auth;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service // IoC 등록
public class PrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" ----- 실행 {}", getClass().getName());

        // UserRepository 에 username의 존재 여부 확인
        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            log.info(" @@@@@ NO USER");
            return null;
        } else {
            log.info(" @@@@@ [{}] {}, {}", userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());

            return new PrincipalDetails(userEntity);
        }
    }
}
