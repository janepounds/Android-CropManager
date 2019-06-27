package com.myfarmnow.myfarmcrop.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.CropLoginActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

import java.util.ArrayList;

public class CropNotificationsFireService extends Service {
    public CropNotificationsFireService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Query the database and show alarm if it applies

        // Here you can return one of some different constants.
        // This one in particular means that if for some reason
        // this service is killed, we don't want to start it
        // again automatically

        ArrayList<CropNotification> todayNotifications = MyFarmDbHandlerSingleton.getHandlerInstance(this).getCropNotifications(CropDashboardActivity.getPreferences("userId",this), CropNotification.QUERY_KEY_TODAY);
        ArrayList<CropNotification> upcomingNotifications = MyFarmDbHandlerSingleton.getHandlerInstance(this).getCropNotifications(CropDashboardActivity.getPreferences("userId",this), CropNotification.QUERY_KEY_REPORT_FROM_TODAY);

        showNotifications(todayNotifications, "Tasks due Today","TODAY",1);
        showNotifications(upcomingNotifications, "Upcoming Tasks","UPCOMING",2);

        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return null;
    }
    @Override
    public void onDestroy() {
        // I want to restart this service again in 24 hours
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+(1000 * 60 *60 * 12),
                PendingIntent.getService(this, 0, new Intent(this, CropNotificationsFireService.class), 0)
        );
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

        NotificationManager notifManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);
            intent = new Intent(this, CropLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            builder

                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentTitle(title)// required
                    .setContentText(this.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentText(upComingMessage)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(upComingMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(this, id);
            intent = new Intent(this, CropLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            builder
                    .setContentTitle(title)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentText(upComingMessage)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(upComingMessage))
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
