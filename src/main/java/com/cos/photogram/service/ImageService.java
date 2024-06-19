package com.cos.photogram.service;

import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.image.ImageRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true)
    public List<Image> loadImageList(int fromUserId, Pageable pageable) {
        List<Image> imageList = imageRepository.selectImageList(fromUserId, pageable);

        imageList.forEach((image -> {
            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == fromUserId) {
                    image.setLikeState(true);
                }
            });
            image.setLikeCount(image.getLikes().size());
        }));
        return imageList;
    }

    @Transactional
    public void uploadImage(ImageUploadDto imageUploadDto, User user) {
        // 이미지는 파일 형태로 서버에 저장되고, DB에는 경로만 저장된다.
        MultipartFile uploadFile = imageUploadDto.getFile();

        // 이미지 파일명이 동일할 경우를 대비하여 UUID를 활용
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + uploadFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + filename);

        log.info(" @@@@@ imageFilePath : {} ", imageFilePath);

        try {
            Files.write(imageFilePath, uploadFile.getBytes());
        } catch (IOException e) {
            //
        }

        // DB 저장 ImageUploadDto -> image -> imageRepository.save()
        Image imageEntity = imageUploadDto.toEntity(filename, user);
        imageRepository.save(imageEntity);

    }
}
