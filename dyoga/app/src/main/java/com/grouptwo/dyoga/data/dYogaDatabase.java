package com.grouptwo.dyoga.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.grouptwo.dyoga.data.Entities.Course;
import com.grouptwo.dyoga.data.converters.Converters;
import com.grouptwo.dyoga.data.dao.CourseDao;


@Database(entities = {Course.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class dYogaDatabase extends RoomDatabase {

    private static dYogaDatabase INSTANCE;
    public abstract CourseDao courseDao();

    public static dYogaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (dYogaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    dYogaDatabase.class, "dyoga_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
