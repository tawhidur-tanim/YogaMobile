package com.grouptwo.dyoga.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.grouptwo.dyoga.data.converters.Converters;
import com.grouptwo.dyoga.data.dao.ClassInstanceDao;
import com.grouptwo.dyoga.data.dao.CourseDao;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.data.entities.Course;

@TypeConverters({Converters.class})
@Database(entities = {Course.class, ClassInstance.class}, version = 10, exportSchema = false)
public abstract class dYogaDatabase extends RoomDatabase {

    private static volatile dYogaDatabase INSTANCE;

    public abstract CourseDao courseDao();
    public abstract ClassInstanceDao classInstanceDao();

    public static dYogaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (dYogaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    dYogaDatabase.class, "dyoga_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

