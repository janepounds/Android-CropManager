package com.myfarmnow.myfarmcrop.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myfarmnow.myfarmcrop.services.CropNotificationsCreatorService;
import com.myfarmnow.myfarmcrop.services.CropNotificationsFireService;

public class CropBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, CropNotificationsFireService.class));
            context.startService(new Intent(context, CropNotificationsCreatorService.class));
        }
    }
}