package com.auro.scholr.util.alert_dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.databinding.CustomSnackbarBinding;
import com.google.android.material.snackbar.Snackbar;
import com.auro.scholr.R;


public class ErrorSnackbar {


    private ErrorSnackbar() {

    }

    public static View showSnackbar(View relative, String msg) { // Create the Snackbar
        CustomSnackbarBinding binding;
        Snackbar snackbar = Snackbar.make(relative, "", Snackbar.LENGTH_LONG);

        //inflate view
        LayoutInflater inflater = (LayoutInflater) relative.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View snackView = inflater.inflate(R.layout.custom_snackbar, null);
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false);
        // White background
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        //TextView tv_msg = snackView.findViewById(R.id.textMsg);
        binding.textMsg.setText(msg);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        if (binding.getRoot().getParent() != null){
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());

        }
        snackBarView.addView(binding.getRoot(), 0);
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

        return binding.getRoot();

    }
    public static View showSnackbar(View relative, String msg,int color) { // Create the Snackbar
        CustomSnackbarBinding binding;
        Snackbar snackbar = Snackbar.make(relative, "", Snackbar.LENGTH_LONG);

        //inflate view
        LayoutInflater inflater = (LayoutInflater) relative.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View snackView = inflater.inflate(R.layout.custom_snackbar, null);
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false);
        // White background
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        snackbar.getView().setBackgroundColor(color);
        //TextView tv_msg = snackView.findViewById(R.id.textMsg);
        binding.textMsg.setText(msg);
        binding.background.setBackgroundColor(color);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        if (binding.getRoot().getParent() != null){
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());

        }
        snackBarView.addView(binding.getRoot(), 0);
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

        return binding.getRoot();

    }


}
