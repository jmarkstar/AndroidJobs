package com.jmarkstar.jobs.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.jmarkstar.jobs.model.LogModel;
import java.util.List;

/**
 * Created by jmarkstar on 14/01/2018.
 */
@Dao
public interface LogDao {

    @Query("SELECT * FROM "+ LogModel.TABLE_NAME+" ORDER BY createdAt DESC")
    List<LogModel> getAll();

    @Query("DELETE FROM "+LogModel.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLog(LogModel log);
}
