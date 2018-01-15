package com.jmarkstar.jobs.loggers.firebase_job_dispatcher;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.jmarkstar.jobs.database.LogDao;
import com.jmarkstar.jobs.database.LogDatabase;
import com.jmarkstar.jobs.database.async.InsertAsync;
import com.jmarkstar.jobs.model.LogModel;
import com.jmarkstar.jobs.view.MainActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jmarkstar on 14/01/2018.
 */
public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

    @Override public boolean onStartJob(JobParameters job) {
        Date date = new Date();
        String dateTime = sdf.format(date);
        Log.v( TAG, "Job was executed on "+dateTime);

        LogModel log = new LogModel(dateTime, date);
        LogDao logDao = LogDatabase.getAppDatabase(this).userDao();

        new InsertAsync(logDao, log).execute();

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.REFRESH_ACTION));

        return false;
    }

    @Override public boolean onStopJob(JobParameters job) {
        Log.v( TAG, "Job was stopped");
        return false;//should this job be retried?
    }
}
