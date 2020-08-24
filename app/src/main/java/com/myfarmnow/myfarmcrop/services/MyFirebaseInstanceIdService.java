package com.myfarmnow.myfarmcrop.services;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        DashboardActivity.sendFirebaseToken(s,this);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }
}