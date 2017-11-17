package com.foodlog;

import com.foodlog.scheduledmeal.ScheduledMeal;
import com.foodlog.scheduledmeal.ScheduledMealAdapter;
import com.foodlog.sender.Sender;
import com.foodlog.sender.sentmessage.SentMessage;
import com.foodlog.sender.sentmessage.SentMessageRepository;
import com.foodlog.sender.sentmessage.SentMessageService;
import com.foodlog.user.UserTelegram;
import com.foodlog.user.UserTelegramAdapter;
import com.foodlog.wsAdapter.Adapter;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rafael on 16/06/17.
 */
@Service
public class ScheduledMealBatch {

    @Autowired
    private Adapter adapter;

    @Autowired
    private ScheduledMealAdapter scheduledMealAdapter;
    @Autowired
    private UserTelegramAdapter userTelegramAdapter;
    @Autowired
    private SentMessageService sentMessageService;



    public void run() {
        System.out.println("########## here we gooooooo  :" + new Date());

        adapter.setHost("http://localhost:8080");


        sentMessageService.clearAllByPastDays(1, this.getClass().toString());

        //busca os scheduled de todos os usuarios que vao ocorrer de agora ate daqui ha 20 min
        for (ScheduledMeal scheduledMeal : scheduledMealAdapter.getAllScheduledMealsInWindow(20)) {


            String msg = scheduledMeal.getName()
                    + "(" + scheduledMeal.getTargetTime() + "):   "
                    + scheduledMeal.getDescription();


            String messageid = msg + scheduledMeal.getUser().getId();

            if (!sentMessageService.isSent(messageid)) {
                UserTelegram user = userTelegramAdapter.getUserByLogin(scheduledMeal.getUser());

                try {
                    new Sender(TelegramConfig.BOT_ID).sendResponse(user.getTelegramId(), msg);
                    sentMessageService.logSentMessage(messageid, this.getClass().toString());
                } catch (IOException ex) {
                    System.out.println("errrxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        }
    }
}
