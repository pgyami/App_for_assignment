package com.example.kuson.app_for_assignment;

/**
 * Created by asus on 4/17/2015.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";       //Thông báo ID của Notification
    public static String NOTIFICATION = "notification";             //Thông báo object Notification được sử dụng

    public void onReceive(Context context, Intent intent) {         //Gửi thông báo tới cho hệ điều hành

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}