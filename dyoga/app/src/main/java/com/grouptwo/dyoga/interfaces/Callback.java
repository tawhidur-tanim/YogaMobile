package com.grouptwo.dyoga.interfaces;

import java.util.List;

public interface Callback<T> {
    default void onResult(T result) {
        throw new UnsupportedOperationException();
    }

    default void onResult(List<T> result) {
        throw new UnsupportedOperationException();
    }
}