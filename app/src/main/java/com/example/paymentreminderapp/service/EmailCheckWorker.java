package com.example.paymentreminderapp.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class EmailCheckWorker extends Worker {
    public EmailCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        System.out.println("WORKING WORKER _____________________________________________");

        return Result.success();
    }
}
