package com.cos.photogram.service;

import com.cos.photogram.domain.handler.ex.CustomApiException;
import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Value("${file.path}")
    private String uploadFolder;

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

    @Transactional
    public User updateProfileImage(Integer loginUserId, MultipartFile profileImageFile) {

        UUID uuid = UUID.randomUUID();
        String profileImagePath = uuid + "_" + profileImageFile.getOriginalFilename();

        try {
            Files.write(Path.of(uploadFolder + profileImagePath), profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

         User userEntity = userRepository.findById(loginUserId).orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 유저입니다.");
        });

        userEntity.setProfileImageUrl(profileImagePath);

        return userEntity;

    }
}
