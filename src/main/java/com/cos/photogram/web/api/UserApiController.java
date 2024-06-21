package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.SubscribeService;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController // api 응답 시 사용
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    //구독 모달 클릭 시 유저의 구독 정보를 불러와야할 떄 사용
    @GetMapping("/{profileId}/subscribe")
    public ResponseEntity<?> loadSubscribeInfo(@PathVariable int profileId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // profileId : 조회할 유저 정보
        // principalDetails : 로그인한 유저 정보
        List<SubscribeDto> subscribeDtoList = subscribeService.loadSubscribeList(profileId, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "구독 정보 로딩 완료", subscribeDtoList), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public CMRespDto<?> update(@PathVariable Integer id
                                , @Valid UserUpdateDto userUpdateDto
                                , BindingResult bindingResult
                                , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info(" @@@@@ update controller!!! \n{}", userUpdateDto.toString());

        User user = userUpdateDto.toEntity();

        // user info update logic
        User userEntity = userService.updateUserInfo(id, user);
        
        // session 정보 업데이트
        principalDetails.setUser(userEntity);
        
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }

    @PutMapping("/{loginUserId}/profileImage")
    public ResponseEntity<?> putProfileImage(@PathVariable Integer loginUserId,
                                             MultipartFile profileImageFile) {

        userService.updateProfileImage(loginUserId, profileImageFile);

        return new ResponseEntity<>(new CMRespDto<>(1, "프로필 이미지 저장 성공", null), HttpStatus.OK);
    }
}
