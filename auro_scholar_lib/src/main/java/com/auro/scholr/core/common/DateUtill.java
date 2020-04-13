package com.auro.scholr.core.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtill {

   public static void showDateDialogForDOB(Context context, final DateActionListener dateActionListener){
       final Calendar myCalendar = Calendar.getInstance();
       DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

           @Override
           public void onDateSet(DatePicker view, int year, int monthOfYear,
                                 int dayOfMonth) {
               myCalendar.set(Calendar.YEAR, year);
               myCalendar.set(Calendar.MONTH, monthOfYear);
               myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

               String myFormat = "dd/MM/yyyy"; //In which you need put here
               SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
               if(dateActionListener != null)
                dateActionListener.getDate(sdf.format(myCalendar.getTime()));

           }

       };
       DatePickerDialog datePickerDialog =  new DatePickerDialog(context, date, myCalendar
               .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
               myCalendar.get(Calendar.DAY_OF_MONTH));

       Calendar maxDate = Calendar.getInstance();
       maxDate.add(Calendar.YEAR, -15);

       datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
       datePickerDialog.show();
   }


    public interface DateActionListener{
       void getDate(String date);
    }
}
