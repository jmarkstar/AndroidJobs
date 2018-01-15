package com.jmarkstar.jobs.loggers.firebase_job_dispatcher;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by jmarkstar on 14/01/2018.
 */

public class FirebaseJobDispatcherController {

    private static final String TAG_JOB = "my-unique-tag";

    private FirebaseJobDispatcher dispatcher;

    public FirebaseJobDispatcherController(Context context){
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public void start(int secondsIntervals){
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag(TAG_JOB)        // uniquely identifies the job
                .setReplaceCurrent(true)       // don't overwrite an existing job with the same tag
                .setRecurring(true)            // one-off job
                .setLifetime(Lifetime.FOREVER)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(secondsIntervals, secondsIntervals+5))// repeated every {secondsIntervals} seconds with 5 second of tollerance
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();

        dispatcher.mustSchedule(myJob);
    }

    public void stop(){
        dispatcher.cancel(TAG_JOB);
    }
}
