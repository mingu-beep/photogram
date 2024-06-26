package com.cos.photogram.web.dto.auth;

import com.cos.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {

    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .role("ROLE_USER")
                .build();
    }
}
