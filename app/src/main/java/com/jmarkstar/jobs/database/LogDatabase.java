package com.jmarkstar.jobs.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.jmarkstar.jobs.model.LogModel;

/**
 * Created by jmarkstar on 14/01/2018.
 */
@Database(entities = {LogModel.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class LogDatabase extends RoomDatabase {

    private static LogDatabase INSTANCE;


    public static LogDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LogDatabase.class, "log_database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract LogDao userDao();
}
