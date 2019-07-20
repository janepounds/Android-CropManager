package com.myfarmnow.myfarmcrop.services;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;

public class BackupWorker extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {
        getApplicationContext(). startService(new Intent(getApplicationContext(), CropSyncService.class));
        return WorkerResult.SUCCESS;
    }
}
