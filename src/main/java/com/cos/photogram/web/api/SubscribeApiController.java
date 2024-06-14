package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.service.SubscribeService;
import com.cos.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 구독 요청 (Post), 구독 취소 (DELETE)
 */

@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    /**
     * 구독 요청시 필요한 정보
     * @param principalDetails 현재 로그인한 유저 정보
     * @param toUserId 구독하고자 하는 유저 아이디
     * @return
     */
    @PostMapping("/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Integer toUserId) {

        Integer res = subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);
        
        return res == null ?
                new ResponseEntity<>(new CMRespDto<>(1, "재귀적 구독 요청", null), HttpStatus.ACCEPTED)
                : new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공", null), HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param principalDetails 현재 로그인한 유저 정보
     * @param toUserId 구독 취소하고자 하는 유저 아이디
     * @return
     */
    @DeleteMapping("/{toUserId}")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Integer toUserId) {
        subscribeService.unsubscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "구독취소하기 성공", null), HttpStatus.ACCEPTED);
    }

}
