package com.cos.photogram.service;

import com.cos.photogram.domain.handler.ex.CustomApiException;
import com.cos.photogram.domain.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public Integer subscribe(Integer fromUserId, Integer toUserId) {
        try {
            if(fromUserId.equals(toUserId)) {
                return null;
            } else {
                return subscribeRepository.insertSubscribe(fromUserId, toUserId);
            }
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 사용자입니다.");
        }

    }

    @Transactional
    public void unsubscribe(Integer fromUserId, Integer toUserId) {
        subscribeRepository.deleteSubscribe(fromUserId, toUserId);
    }
}
