package com.foodlog.sender.sentmessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Created by rafael on 17/11/17.
 */
@Repository
public interface SentMessageRepository extends JpaRepository<SentMessage,Integer> {
/*
    void deleteBySentDateTimeBeforeAndMessageType(Instant minus, String messageType);
*/
    void deleteByTargetAndMessageType(String target, String messageType);

    @Transactional
    void deleteBySentDateTimeBefore(Instant minus);

    List<SentMessage> findByTargetAndTextHashAndMessageType(String target, int textHash, String messageType);
}
