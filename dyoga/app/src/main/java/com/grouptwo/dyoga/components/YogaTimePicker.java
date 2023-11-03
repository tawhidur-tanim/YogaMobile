package com.grouptwo.dyoga.components;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.grouptwo.dyoga.interfaces.YogaPickerSetListener;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class YogaTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TIME_FORMAT = "hh:mm a";
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private LocalDateTime localDateTime;
    private final static String TIME_PICKER_TAG = "yogaTimePickerTag";

    public YogaTimePicker(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(getContext(), this, localDateTime.getHour(),
                localDateTime.getMinute(), false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        localDateTime = localDateTime.withHour(hourOfDay).withMinute(minute);
        onTimeSetListener.onTimeSet(view, hourOfDay, minute);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getTimeText() {
        return DateTimeFormatter.ofPattern(TIME_FORMAT).format(localDateTime);
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    public static void show(final LocalDateTime localDateTime,
                            final YogaPickerSetListener<YogaTimePicker> yogaTimePickerSetListener,
                            final FragmentManager fragmentManager) {

        final YogaTimePicker yogaTimePicker = new YogaTimePicker(localDateTime);
        yogaTimePicker.setOnTimeSetListener((view, hourOfDay, minute) ->
                yogaTimePickerSetListener.set(yogaTimePicker));
        yogaTimePicker.show(fragmentManager, TIME_PICKER_TAG);
    }

    public static String getCurrentTimeText() {
        return DateTimeFormatter.ofPattern(TIME_FORMAT).format(LocalDateTime.now());
    }

    public static String getTimeText(LocalDateTime yogaTime) {
        return DateTimeFormatter.ofPattern(TIME_FORMAT).format(yogaTime);
    }
}
