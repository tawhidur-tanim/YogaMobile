package com.grouptwo.dyoga.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonConfig {

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

    public static Gson gson(){
        GSON_BUILDER.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        GSON_BUILDER.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return GSON_BUILDER.setPrettyPrinting().create();
    }
}
