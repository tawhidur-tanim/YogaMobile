package com.grouptwo.dyoga.interfaces;

import android.view.View;

public interface ItemClickListener<T> {

    void onItemClicked(View v, int itemPosition, T t);
}
