package com.example.taskmanagement.worker;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TaskScheduler {

    @SuppressLint("ScheduleExactAlarm")
    public void scheduleNotification(Context context, String taskTitle, long dueTime, long taskId) {

        long triggerTime = dueTime - 600000;

        long currentTime = System.currentTimeMillis();
        if (triggerTime <= currentTime) {
            return;
        }

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("taskTitle", taskTitle);
        intent.putExtra("dueTime", dueTime);
        intent.putExtra("taskId", taskId);
        int intValue = (int) taskId;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, intValue, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}
