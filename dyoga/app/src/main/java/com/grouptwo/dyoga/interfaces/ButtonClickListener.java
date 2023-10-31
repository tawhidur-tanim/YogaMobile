package com.grouptwo.dyoga.interfaces;

import android.view.View;

public interface ButtonClickListener<T> {

    public void onEditClick(View view, int itemPosition, T item);

    public void onDeleteClick(View view, int itemPosition, T item);
}
