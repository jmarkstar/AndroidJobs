package com.jmarkstar.jobs.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

/**
 * Created by jmarkstar on 14/01/2018.
 */
@Entity(tableName = LogModel.TABLE_NAME)
public class LogModel {

    public static final String TABLE_NAME = "log";

    @PrimaryKey(autoGenerate = true)
    public final Integer uid;
    @ColumnInfo(name = "msj")
    public final String message;
    public final Date createdAt;

    public LogModel( Integer uid, String message, Date createdAt) {
        this.uid = uid;
        this.message = message;
        this.createdAt = createdAt;
    }

    @Ignore
    public LogModel(String message, Date createdAt) {
        this.uid = null;
        this.message = message;
        this.createdAt = createdAt;
    }
}
