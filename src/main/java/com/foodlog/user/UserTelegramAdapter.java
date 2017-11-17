package com.foodlog.user;

import com.foodlog.scheduledmeal.ScheduledMeal;
import com.foodlog.wsAdapter.Adapter;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by rafael on 17/11/17.
 */
@Service
public class UserTelegramAdapter extends Adapter {

    public UserTelegram getUserByLogin(User user) {
        String url = "/api/batch/user-telegrams/" + user.getId();
        return this.doGet(url, UserTelegram.class);

    }
}
