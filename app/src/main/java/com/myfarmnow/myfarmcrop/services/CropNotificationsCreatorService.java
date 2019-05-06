package com.myfarmnow.myfarmcrop.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

import java.util.ArrayList;

public class CropNotificationsCreatorService extends Service {
    public CropNotificationsCreatorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Query the database and show alarm if it applies

        // Here you can return one of some different constants.
        // This one in particular means that if for some reason
        // this service is killed, we don't want to start it
        // again automatically

        MyFarmDbHandlerSingleton.getHandlerInstance(this).generateNotifications();

        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // I want to restart this service again in 24 hours
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+(1000 * 60 *60 *24),
                PendingIntent.getService(this, 0, new Intent(this, CropNotificationsCreatorService.class), 0)
        );
    }
}
