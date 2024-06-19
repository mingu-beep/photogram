package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.service.ImageService;
import com.cos.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<?> loadImageStory(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 3) Pageable pageable) {
        List<Image> imageList = imageService.loadImageList(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "로딩 성공", imageList), HttpStatus.OK);
    }
}
