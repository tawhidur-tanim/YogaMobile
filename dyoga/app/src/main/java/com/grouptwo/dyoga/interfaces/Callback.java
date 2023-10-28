package com.grouptwo.dyoga.interfaces;

import java.util.List;

public interface Callback<T> {
    void onResult(T result);

    void onResult(List<T> result);
}