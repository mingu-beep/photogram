package com.cos.photogram.service;

import com.cos.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void like(int imageId, int userId) {
        likesRepository.insertLikes(imageId, userId);
    }

    @Transactional
    public void unlike(int imageId, int userId) {
        likesRepository.deleteLikes(imageId, userId);
    }
}
