package com.example.user.appfinal.sync;

import android.content.Context;

import com.example.user.appfinal.utilities.NotificationUtils;

/**
 * Created by user on 2017/6/18.
 */

public class ReminderTasks {

    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    static final String ACTION_CHARGING_REMINDER = "charging-reminder";

    public static void executeTask(Context context, String action) {

            NotificationUtils.clearAllNotifications(context);

    }

}
