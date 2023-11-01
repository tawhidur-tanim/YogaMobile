package com.grouptwo.dyoga.data.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    @TypeConverter
    public static String toString(LocalDateTime localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @TypeConverter
    public static LocalDateTime toLocalDate(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
