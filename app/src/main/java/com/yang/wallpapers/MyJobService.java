package com.yang.wallpapers;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class MyJobService extends JobService{
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JobInfo.Builder builder =new JobInfo.Builder(1,new ComponentName(this, MyJobService.class));
        builder.setOverrideDeadline(0);
        builder.setPersisted(true);
        JobScheduler jobScheduler = (JobScheduler)this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
