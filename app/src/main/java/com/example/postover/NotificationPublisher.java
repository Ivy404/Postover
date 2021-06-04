package com.example.postover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.Notification;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        String title = "";
        String content = "";
        int notificationId = 0;
        Bundle extras = intent.getExtras();
        if(extras != null){
            title = extras.getString("title");
            content = extras.getString("content");
            notificationId = extras.getInt("id");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setGroup("POSTOVER");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}
