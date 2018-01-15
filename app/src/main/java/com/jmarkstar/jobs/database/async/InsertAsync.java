package com.jmarkstar.jobs.database.async;

import android.os.AsyncTask;
import com.jmarkstar.jobs.database.LogDao;
import com.jmarkstar.jobs.model.LogModel;

/**
 * Created by jmarkstar on 14/01/2018.
 */

public class InsertAsync extends AsyncTask<Void, Void, Void> {
    private LogDao logDao;
    private LogModel log;

    public InsertAsync(LogDao logDao, LogModel log){
        this.logDao = logDao;
        this.log = log;
    }

    @Override protected Void doInBackground(Void... voids) {
        logDao.insertLog(log);
        return null;
    }

    @Override protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
