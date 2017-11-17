package com.foodlog.sender.sentmessage;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

/**
 * Created by rafael on 09/06/17.
 */

@Entity
@Table(name = "sent_message")
public class SentMessage {
    @Id
    private Integer id;

    private String text;
    private String messageType;
    private Instant sentDateTime;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
