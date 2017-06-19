package com.example.user.appfinal.sync;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2017/6/18.
 */

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "food_reminder_tag";
    private static final String READY__TAG = "ready-sync";

    private static boolean sInitialized;



    synchronized public static void scheduleChargingReminder(@NonNull final Context context,
                                                             int taskId,
                                                             int hourOfDay,
                                                             int minute,
                                                             int second)  {

//        If the job has already been initialized, return
        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Calendar now = Calendar.getInstance();
        Calendar schedule = Calendar.getInstance();
        schedule.set(Calendar.HOUR_OF_DAY, hourOfDay);
        schedule.set(Calendar.MINUTE, minute);
        schedule.set(Calendar.SECOND, second);

        int scheduleTime = (int) ((schedule.getTimeInMillis() - now.getTimeInMillis()) / 1000);

        Bundle bundle = new Bundle();
        bundle.putInt("ID", taskId);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(FoodReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(
                        scheduleTime,
                        scheduleTime+10))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;


    }


    public static void cancelSchedule(@NonNull final Context context,  int id) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        dispatcher.cancel(READY__TAG + id);

    }
}
