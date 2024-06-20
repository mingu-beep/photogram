package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.handler.ex.CustomValidationException;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.service.ImageService;
import com.cos.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/story")
    public String storyPage() {
        return "image/story";
    }

    @GetMapping("/popular")
    public String popularPage(Model model) {

        List<Image> imageList = imageService.loadPopularImage();
        model.addAttribute("images", imageList);
        return "image/popular";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "image/upload";
    }

    /**
     * 이미지 저장시 필요한 데이터
     * @param imageUploadDto : Image Upload Form 으로부터 넘어온 데이터
     * @param principalDetails : 로그인되어있는 회원 정보 (세션)
     * @return : 이동할 페이지
     */
    @PostMapping
    public String upload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 선택되지 않았습니다.");
        }

        imageService.uploadImage(imageUploadDto, principalDetails.getUser());

        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}
