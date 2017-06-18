package com.example.user.appfinal.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.example.user.appfinal.ChildActivity;
import com.example.user.appfinal.R;

/**
 * Created by user on 2017/6/18.
 */

public class NotificationUtils {
    private static final int REFRIGERATOR_REMINDER_NOTIFICATION_ID = 1400;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void notifyUser(Context context) {
        // 點擊通知會到ChildActivity介面
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, new Intent(context, ChildActivity.class), 0);


        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.alert_sub))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();


        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(REFRIGERATOR_REMINDER_NOTIFICATION_ID, notification);
    }



}
