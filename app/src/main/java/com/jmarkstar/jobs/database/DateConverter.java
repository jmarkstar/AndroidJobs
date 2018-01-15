package com.jmarkstar.jobs.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by jmarkstar on 14/01/2018.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
