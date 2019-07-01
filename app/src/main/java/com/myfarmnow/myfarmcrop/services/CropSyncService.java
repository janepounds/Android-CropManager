package com.myfarmnow.myfarmcrop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;

import java.util.ArrayList;

public class CropSyncService extends Service {
    MyFarmDbHandlerSingleton dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
    String userId = null;
    public CropSyncService() {
        userId = CropDashboardActivity.getPreferences(CropDashboardActivity
        .PREFERENCES_USER_ID, this);


    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    public void prepareSyncRequest(){
        ArrayList<CropField> fields = dbHandler.getCropFields(userId,false);





    }
}
