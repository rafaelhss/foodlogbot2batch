package com.foodlog.scheduledmeal;

import com.foodlog.wsAdapter.Adapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafael on 17/11/17.
 */
@Component
public class ScheduledMealAdapter extends Adapter {

    public List<ScheduledMeal> getAllScheduledMealsInWindow(int minuteWindow) {

        String url = "/api/batch/scheduled-meals?minute-window=" + minuteWindow;
        return Arrays.asList(this.doGet(url, ScheduledMeal[].class));

    }
}
