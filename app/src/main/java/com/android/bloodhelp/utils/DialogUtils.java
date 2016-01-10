package com.android.bloodhelp.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by gspl on 28-12-2015.
 */
public final class DialogUtils
{
    private DialogUtils()
    {

    }

    public static void datePickerDialog(Context context, final EditText et) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String temp = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        et.setText(temp);
                    }

                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, Calendar.JANUARY);
        c.add(Calendar.YEAR, -18);
        dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        dateDialog.show();
        dateDialog.setCancelable(false);
    }
}
