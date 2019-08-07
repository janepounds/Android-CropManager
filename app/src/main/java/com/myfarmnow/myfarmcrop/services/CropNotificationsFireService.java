package com.myfarmnow.myfarmcrop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CropNotificationsFireService extends Service {
    public CropNotificationsFireService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
