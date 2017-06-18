package com.example.user.appfinal.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.example.user.appfinal.utilities.NotificationUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by user on 2017/6/18.
 */

public class FoodReminderFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask() {

            @Override
            protected Void doInBackground(Object[] params) {

                Context context = getApplicationContext();
                NotificationUtils.notifyUser(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        return true;
    }
}
