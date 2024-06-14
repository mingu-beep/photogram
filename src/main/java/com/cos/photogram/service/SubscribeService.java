package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public String subscribe(Integer fromUserId, Integer toUserId) {

        subscribeRepository.insertSubscribe(fromUserId, toUserId);
        return null;
    }

    @Transactional
    public String unsubscribe(Integer fromUserId, Integer toUserId) {
        subscribeRepository.deleteSubscribe(fromUserId, toUserId);
        return null;
    }
}
