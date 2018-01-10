package com.foodlog.meallog;

import com.foodlog.scheduledmeal.ScheduledMeal;
import com.foodlog.wsAdapter.Adapter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafael on 10/01/18.
 */
@Service
public class MealLogAdapter extends Adapter {
    public List<MealLog> getLastElapsedUserMealLogs(int elapsedHours) {

        String url = "/api/batch/meal-logs?elapsed-hours=" + elapsedHours + "&remove-photo=true";
        return Arrays.asList(this.doGet(url, MealLog[].class));

    }
}
