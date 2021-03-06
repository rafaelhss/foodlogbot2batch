package com.foodlog;

import com.foodlog.config.TelegramConfig;
import com.foodlog.meallog.MealLog;
import com.foodlog.meallog.MealLogAdapter;
import com.foodlog.scheduledmeal.ScheduledMeal;
import com.foodlog.sender.Sender;
import com.foodlog.sender.sentmessage.SentMessageService;
import com.foodlog.user.UserTelegram;
import com.foodlog.user.UserTelegramAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Created by rafael on 10/01/18.
 */
@Service
public class ReportElapsedMealTimeBatch {

    @Autowired
    public MealLogAdapter mealLogAdapter;
    @Autowired
    private UserTelegramAdapter userTelegramAdapter;
    @Autowired
    private Sender sender;

    @Autowired
    private SentMessageService sentMessageService;

    public void run() {
        System.out.println("########## here we ReportElapsedMealTimeBatch  :" + new Date());

        for (MealLog mealLog : mealLogAdapter.getLastElapsedUserMealLogs(3)) {
            long elapsedMealTime = Duration.between(mealLog.getMealDateTime(), Instant.now()).getSeconds() / (60 * 60);
            String msg = "Hora de comer! A ultima refeição ("+ mealLog.getId() +") foi há " + elapsedMealTime + " horas.";

            if(!sentMessageService.isSent(mealLog.getUser(), msg, "ELAPSED" + mealLog.getId())){
                if(sender.sendResponse(mealLog.getUser(), msg)){
                     sentMessageService.logSentMessage(mealLog.getUser(), msg, "ELAPSED" + mealLog.getId());
                }
            }

        }
    }
}
