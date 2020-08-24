package com.myfarmnow.myfarmcrop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class CropNotificationsCreatorService extends Service {
    public CropNotificationsCreatorService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // I want to restart this service again in 24 hours
    }
}
