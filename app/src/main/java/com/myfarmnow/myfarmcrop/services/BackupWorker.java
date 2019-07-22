package com.myfarmnow.myfarmcrop.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackupWorker extends Worker {
Context context;
    public BackupWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        context. startService(new Intent(context, CropSyncService.class));
        Log.d("WORK RAN","THE WORKER RAN FINALLY");
        return Result.success();
    }
}
