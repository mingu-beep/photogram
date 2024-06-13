package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.handler.ex.CustomValidationException;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController // api 응답 시 사용
@RequestMapping("/api/user")
public class UserApiController {

    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public CMRespDto<?> update(@PathVariable Integer id
                                , @Valid UserUpdateDto userUpdateDto
                                , BindingResult bindingResult
                                , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.error(" XXXXX {}", error.getDefaultMessage());
            }

            throw new CustomApiValidationException("입력된 값이 유효하지 않습니다.", errorMap);
        }

        log.info(" @@@@@ update controller!!! \n{}", userUpdateDto.toString());

        User user = userUpdateDto.toEntity();

        // user info update logic
        User userEntity = userService.updateUserInfo(id, user);
        
        // session 정보 업데이트
        principalDetails.setUser(userEntity);
        
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }

}
