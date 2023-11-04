package com.grouptwo.dyoga.utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Constants {

    public static final String USERNAME = "username";
    public static final String COURSE = "course";
    public static final String CLASS_INSTANCE = "class_instance";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String courseCollectionName(String username) {
        return username.substring(0, 6).concat("_courses");
    }

    public static String classInstanceCollectionName(String username) {
        return username.substring(0, 6).concat("_classInstances");
    }

    public static String generateReference() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public static String generateReferenceById(final long id) {
        return UUID.nameUUIDFromBytes(String.valueOf(id).getBytes(StandardCharsets.UTF_8))
                .toString().replace("-", "").substring(0, 6);
    }
}
