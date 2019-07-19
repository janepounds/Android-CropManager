package com.myfarmnow.myfarmcrop.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.services.CropNotificationsCreatorService;
import com.myfarmnow.myfarmcrop.services.CropNotificationsFireService;

public class CropBroadcastsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MY FARM   RECEIVED",intent.getAction());
        Toast.makeText(context, "Intent Detected ."+intent.getAction(), Toast.LENGTH_LONG).show();

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, CropNotificationsFireService.class));
            context.startService(new Intent(context, CropNotificationsCreatorService.class));
        }

    }
}