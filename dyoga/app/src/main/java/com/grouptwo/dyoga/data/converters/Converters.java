package com.grouptwo.dyoga.data.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {


    private static final ThreadLocal<DateTimeFormatter> threadLocal = ThreadLocal.withInitial(() -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        return formatter;
    });

    @TypeConverter
    public static String toString(LocalDateTime localDate) {
        return threadLocal.get().format(localDate);
    }

    @TypeConverter
    public static LocalDateTime toLocalDate(String string) {
        return LocalDateTime.parse(string, threadLocal.get());
    }


}
