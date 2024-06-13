package com.cos.photogram.web.api;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public CMRespDto<?> update(@PathVariable Integer id, UserUpdateDto userUpdateDto) {

        log.info(" @@@@@ update controller!!! \n{}", userUpdateDto.toString());

        User user = userUpdateDto.toEntity();

        User userEntity = userService.updateUserInfo(id, user);
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }

}
