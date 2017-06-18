package com.example.user.appfinal.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by user on 2017/6/18.
 */

public class FoodReminderIntentService extends IntentService {

    public FoodReminderIntentService() {
        super("WaterReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}