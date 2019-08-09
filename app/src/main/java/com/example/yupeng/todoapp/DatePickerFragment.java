package com.example.yupeng.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
//        inflater.inflate(R.layout.dialog_fragment, container);
////        mDialog = v.findViewById(R.)
//    }

    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";

    private static String DateKey = "DateKey";
    private DatePicker mDatePicker;

    static public DatePickerFragment getInstance(Date tempDate) {
        Bundle arg = new Bundle();
        arg.putSerializable(DateKey, tempDate);

        DatePickerFragment instance = new DatePickerFragment();
        instance.setArguments(arg);
        return instance;
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) return; // ????
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

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
                .setPositiveButton(R.string.date_picker_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .setView(v)
                .create();
    }
}
