package com.foodlog.sender.sentmessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Created by rafael on 17/11/17.
 */
@Repository
public interface SentMessageRepository extends JpaRepository<SentMessage,Integer> {

    @Transactional
    void deleteBySentDateTimeBeforeAndMessageType(Instant minus, String messageType);
}
