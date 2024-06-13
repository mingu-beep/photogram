package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {

    // 필수로 받아야하는 데이터
    private String name;
    private String password;

    // 받지 않아도 되는 데이터
    private String website;
    private String bio;
    private String tel;
    private String gender;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .tel(tel)
                .gender(gender)
                .build();
    }
}
