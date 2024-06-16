package com.cos.photogram.service;

import com.cos.photogram.domain.handler.ex.CustomApiException;
import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private static final Logger log = LoggerFactory.getLogger(SubscribeService.class);
    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<SubscribeDto> loadSubscribeList(int profileId, int loginId) {

        /**
         * 1. loginId
         * 2. loginId
         * 3. profileId
         */
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) subscribeState, ");
        sb.append("if((? = u.id),1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?;");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<SubscribeDto> subscribeDtoList = jpaResultMapper.list(query, SubscribeDto.class);

        log.info("list {} ", subscribeDtoList);
        return subscribeDtoList;
    }

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
