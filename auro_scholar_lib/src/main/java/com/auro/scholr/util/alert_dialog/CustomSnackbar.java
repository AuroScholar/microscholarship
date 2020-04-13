package com.auro.scholr.util.alert_dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.auro.scholr.R;


public class CustomSnackbar {

    private CustomSnackbar() {

    }

     public static View showSnackbar(View relative, String msg) { // Create the Snackbar
        Snackbar snackbar = Snackbar.make(relative, "", Snackbar.LENGTH_LONG);

        //inflate view
        LayoutInflater inflater = (LayoutInflater) relative.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = inflater.inflate(R.layout.custom_snackbar, null);
        // White background
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        TextView tv_msg = snackView.findViewById(R.id.textMsg);
        tv_msg.setText(msg);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();

        snackBarView.addView(snackView, 0);
        snackbar.setDuration(2000);
        snackbar.show();
        snackBarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                snackBarView.getViewTreeObserver().removeOnPreDrawListener(this);
                snackbar.setBehavior(null);
                return true;
            }
        });

         return snackView;

    }


}
