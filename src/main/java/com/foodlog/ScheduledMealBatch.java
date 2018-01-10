package com.foodlog;

import com.foodlog.config.TelegramConfig;
import com.foodlog.scheduledmeal.ScheduledMeal;
import com.foodlog.scheduledmeal.ScheduledMealAdapter;
import com.foodlog.sender.Sender;
import com.foodlog.sender.sentmessage.SentMessageService;
import com.foodlog.user.UserTelegram;
import com.foodlog.user.UserTelegramAdapter;
import com.foodlog.wsAdapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by rafael on 16/06/17.
 */
@Service
public class ScheduledMealBatch {

    @Autowired
    private Adapter adapter;

    @Autowired
    private Sender sender;
    @Autowired
    private ScheduledMealAdapter scheduledMealAdapter;
    @Autowired
    private UserTelegramAdapter userTelegramAdapter;

    public void run() {
        System.out.println("########## here we gooooooo  :" + new Date());

        adapter.setHost("http://localhost:8080");

        //busca os scheduled de todos os usuarios que vao ocorrer de agora ate daqui ha 20 min
        for (ScheduledMeal scheduledMeal : scheduledMealAdapter.getAllScheduledMealsInWindow(20)) {
            String msg = scheduledMeal.getName()
                    + "(" + scheduledMeal.getTargetTime() + "):   "
                    + scheduledMeal.getDescription();


            sender.init(TelegramConfig.BOT_ID).sendResponse(scheduledMeal.getUser(), msg, true);

        }
    }
}
