package com.jmarkstar.jobs.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.jmarkstar.jobs.R;
import com.jmarkstar.jobs.database.LogDao;
import com.jmarkstar.jobs.database.LogDatabase;
import com.jmarkstar.jobs.loggers.firebase_job_dispatcher.FirebaseJobDispatcherController;
import com.jmarkstar.jobs.model.LogModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String REFRESH_ACTION = "com.jmarkstar.jobs.REFRESH_ACTION";

    private static final String TAG = "MainActivity";

    private static final int SECONDS_INTERVAL = 60;

    private RecyclerView rvLogs;
    private LogAdapter logAdapter;

    private FirebaseJobDispatcherController fjdController;

    private BroadcastReceiver onRefresh = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            refreshList();
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logAdapter = new LogAdapter();

        fjdController = new FirebaseJobDispatcherController(this);

        rvLogs = findViewById(R.id.rv_log);
        rvLogs.setLayoutManager(new LinearLayoutManager(this));
        rvLogs.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLogs.setAdapter(logAdapter);
        refreshList();
    }

    @Override protected void onResume() {
        super.onResume();
        IntentFilter iff= new IntentFilter(REFRESH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(onRefresh, iff);
    }

    @Override protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onRefresh);
    }

    public void onStart(View view) {
        fjdController.start(SECONDS_INTERVAL);
        Log.i(TAG, "Job was started");
    }

    public void onStop(View view) {
        fjdController.stop();
        Log.i(TAG, "Job was stopped");
    }

    private void refreshList(){
        LogDao logDao = LogDatabase.getAppDatabase(this).userDao();
        new refreshListAsync(logDao, logAdapter).execute();
    }

    private static class refreshListAsync extends AsyncTask<Void, Void, List<LogModel>> {

        private LogDao logDao;
        private LogAdapter logAdapter;

        refreshListAsync(LogDao logDao, LogAdapter logAdapter){
            this.logDao = logDao;
            this.logAdapter = logAdapter;
        }

        @Override protected List<LogModel> doInBackground(Void... voids) {
            return logDao.getAll();
        }

        @Override protected void onPostExecute(List<LogModel> logModels) {
            super.onPostExecute(logModels);
            logAdapter.addList(logModels);
        }
    }
}
