package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private Boolean isOwnProfile;
    private Integer imageCount;
    private User user;

    private Boolean isSubscribe;
    private Integer subscribeCount;
}
