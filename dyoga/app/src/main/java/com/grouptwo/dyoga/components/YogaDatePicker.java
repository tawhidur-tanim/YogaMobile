package com.grouptwo.dyoga.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.grouptwo.dyoga.interfaces.YogaPickerSetListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class YogaDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private LocalDateTime localDateTime;
    private final static String DATE_PICKER_TAG = "yogaDatePickerTag";

    public YogaDatePicker(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getContext(), this
                ,localDateTime.getYear(),localDateTime.getMonthValue()
                ,localDateTime.getDayOfMonth());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        localDateTime = localDateTime.withYear(year).withMonth(month).withDayOfMonth(dayOfMonth);
        onDateSetListener.onDateSet(view,year,month,dayOfMonth);
    }


    public LocalDateTime getLocalDate() {
        return localDateTime;
    }


    public String getDateText() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT).format(localDateTime);
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;

    }

    public static void show(final LocalDateTime localDateTime,
                            final YogaPickerSetListener<YogaDatePicker> yogaDatePickerSetListener,
                            final FragmentManager fragmentManager) {

        final YogaDatePicker yogaDatePicker = new YogaDatePicker(localDateTime);
        yogaDatePicker.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            yogaDatePickerSetListener.set(yogaDatePicker);
        });
        yogaDatePicker.show(fragmentManager, DATE_PICKER_TAG);
    }


    public static String getCurrentDateText() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now());
    }

}
