package com.cos.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDto {
    /**
     * 구독 모달창 출력 정보
     * 1. userId : 구독 버튼을 통한 구독 요청을 보내기 위함
     * 2. 프로필이미지
     * 3. username
     * 4. 구독 버튼
     *  1. 로그인한 유저와 같을 때 구독 버튼 표출 X
     *  2. 다른 유저일 경우 구독 여부에 따른 구독 버튼 or 구독 취소버튼
     */
    private int id;
    private String username;
    private String profileImageUrl;
    private int subscribeState; // 구독 여부
    private int equalUserState; // 본인 여부
}
