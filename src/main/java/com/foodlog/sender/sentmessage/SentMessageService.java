package com.foodlog.sender.sentmessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rafael on 04/07/17.
 */
@Service
public class SentMessageService {

    @Autowired
    private SentMessageRepository sentMessageRepository;


    public void logSentMessage(String messageid, String messageType) {
        //Log Message
        SentMessage sentMessage = new SentMessage();
        sentMessage.setId(messageid.hashCode());
        sentMessage.setText(messageid.substring(254));
        sentMessage.setSentDateTime(Instant.now());
        sentMessage.setMessageType(messageType);
        sentMessageRepository.save(sentMessage);

    }

    public boolean isSent(String messageid){
        return sentMessageRepository.findOne(messageid.hashCode()) != null;
    }

    public void clearAllByPastDays(int days, String messageType){
        sentMessageRepository.deleteBySentDateTimeBeforeAndMessageType(Instant.now().minus(days, ChronoUnit.DAYS), messageType);
    }





}
