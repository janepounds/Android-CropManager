package com.myfarmnow.myfarmcrop.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

import java.util.ArrayList;

public class CropNotificationsSendWorker extends Worker {
    

    Context context;
    public CropNotificationsSendWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    public CropNotificationsSendWorker(){
        super(null,null);
    }

    @NonNull
    @Override
    public Result doWork() {
        if(context==null){
            return Result.failure();
        }
        try{
            ArrayList<CropNotification> todayNotifications = MyFarmDbHandlerSingleton.getHandlerInstance(context).getCropNotifications(DashboardActivity.RETRIEVED_USER_ID, CropNotification.QUERY_KEY_TODAY);
            ArrayList<CropNotification> upcomingNotifications = MyFarmDbHandlerSingleton.getHandlerInstance(context).getCropNotifications(DashboardActivity.RETRIEVED_USER_ID, CropNotification.QUERY_KEY_REPORT_FROM_TODAY);

            showNotifications(todayNotifications, "Tasks due Today","TODAY",1);
            showNotifications(upcomingNotifications, "Upcoming Tasks","UPCOMING",2);

        }catch (Exception e){
            e.printStackTrace();
            return Result.retry();
        }

        return Result.success();
    }
    


    public void showNotifications(ArrayList<CropNotification> upcomingNotifications, String title,String id, int NOTIFY_ID ){

        String upComingMessage ="";

        for(CropNotification notification : upcomingNotifications){
            upComingMessage+=notification.getActionDate()+" : "+notification.getMessage()+"\n";
        }
        if (upcomingNotifications.size()==0){
            return;// no notifications
        }


        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        NotificationManager notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)// required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentText(upComingMessage)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(upComingMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.logo)   // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentText(upComingMessage)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(upComingMessage))
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        if (notifManager != null) {
            notifManager.notify(NOTIFY_ID, notification);
        }
    }
}
