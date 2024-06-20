package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.handler.ex.CustomException;
import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    @GetMapping("/{profileId}")
    public String profilePage(@PathVariable int profileId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        User userEntity = userRepository.findById(profileId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 사용자에 대한 접근입니다.");
        });

        int subscribeCount = subscribeRepository.selectCountSubscribe(profileId);

        boolean isSubscribe = subscribeRepository.selectWhetherSubscribe(principalDetails.getUser().getId(), profileId) == 1;

        // Profile 페이지에서 출력할 데이터를 위한 DTO
        // 1. 로그인 유저와 Profile 페이지 의 id 일치 여부
        // 2. 게시글 수
        // 3. User 정보
        // 4. 구독 여부
        // 5. 구독자 수

        // 6. 이미지 별 좋아요 수
        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        UserProfileDto userProfileDto = new UserProfileDto(
                (principalDetails.getUser().getId() == profileId)
                , userEntity.getImages().size()
                , userEntity
                , isSubscribe
                , subscribeCount
        );

        model.addAttribute("dto", userProfileDto);
        return "user/profile";
    }

    @GetMapping("/update")
    public String updatePage() {
        return "user/update";
    }
}

