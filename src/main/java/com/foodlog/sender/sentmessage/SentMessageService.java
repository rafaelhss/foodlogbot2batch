package com.foodlog.sender.sentmessage;

import com.foodlog.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rafael on 04/07/17.
 */
@Service
public class SentMessageService {

    @Autowired
    private SentMessageRepository sentMessageRepository;


    public void logSentMessage(User target, String text, String messageType) {
        sentMessageRepository.deleteBySentDateTimeBefore(Instant.now().minus(1, ChronoUnit.DAYS));

        //Log Message
        SentMessage sentMessage = new SentMessage();
        sentMessage.setTextHash(text.hashCode());
        sentMessage.setSentDateTime(Instant.now());
        sentMessage.setMessageType(messageType);
        sentMessage.setTarget(target.getId().toString());
        sentMessageRepository.save(sentMessage);

        System.out.println("Saved: " + target + " | " +  text + " | " + messageType);

    }

    public void clearAllByUserAndType(String target, String messageType){
        sentMessageRepository.deleteByTargetAndMessageType(target, messageType);
    }


    public boolean isSent(User user, String msg, String messageType) {
        List<SentMessage> msgs = sentMessageRepository.findByTargetAndTextHashAndMessageType(user.getId().toString(), msg.hashCode(), messageType);



        System.out.println("isSent "+(msgs != null && msgs.size() > 0) +": " + user + " | " +  msg + " | " + messageType);
        return (msgs != null && msgs.size() > 0);
    }
}
