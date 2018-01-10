package com.foodlog.sender.sentmessage;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by rafael on 09/06/17.
 */

@Entity
@Table(name = "sent_message")
public class SentMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String target;

    private int textHash;
    private String messageType;
    private Instant sentDateTime;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getTextHash() {
        return textHash;
    }

    public void setTextHash(int textHash) {
        this.textHash = textHash;
    }

    public Instant getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
