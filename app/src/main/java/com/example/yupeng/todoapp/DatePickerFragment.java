package com.example.yupeng.todoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
//        inflater.inflate(R.layout.dialog_fragment, container);
////        mDialog = v.findViewById(R.)
//    }

    private static String DateKey = "DateKey";
    private DatePicker mDatePicker;

    static public DatePickerFragment getInstance(Date tempDate) {
        Bundle arg = new Bundle();
        arg.putSerializable(DateKey, tempDate);

        DatePickerFragment instance = new DatePickerFragment();
        instance.setArguments(arg);
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        Date date = (Date) getArguments().getSerializable(DateKey);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment, null);
        mDatePicker = v.findViewById(R.id.date_picker);
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        return new AlertDialog.Builder(getActivity())
                .setTitle("Date Picker")
                .setPositiveButton(R.string.date_picker_ok, null) // why null?
                .setView(v)
                .create();
    }
}
