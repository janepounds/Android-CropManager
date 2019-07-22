package com.myfarmnow.myfarmcrop.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.services.CropNotificationsCreatorService;
import com.myfarmnow.myfarmcrop.services.CropNotificationsSendWorker;



public class CropBroadcastsReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, CropNotificationsSendWorker.class));
            context.startService(new Intent(context, CropNotificationsCreatorService.class));
            CropDashboardActivity.scheduleBackgroundWork(); //
        }

    }
}