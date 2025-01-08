package com.example.taskmanagement.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.taskmanagement.R;
import com.example.taskmanagement.view.activities.AddTaskActivity;
import com.example.taskmanagement.view.activities.MainActivity;

import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskTitle = intent.getStringExtra("taskTitle");
        long dueTime = intent.getLongExtra("dueTime", 0);
        long taskId = intent.getLongExtra("taskId", 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "task_notifications";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Task Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Intent activityIntent = new Intent(context, AddTaskActivity.class);
        activityIntent.putExtra("taskId_Again", taskId);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, (int) taskId, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Task Reminder")
                .setContentText("Task: " + taskTitle + " is due at " + new Date(dueTime).toString())
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify((int) dueTime, notification);
    }
}
