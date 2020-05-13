package com.auro.scholr.util.alert_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.databinding.CustomProgressDialogBinding;
import com.auro.scholr.databinding.DialogCustomBinding;


public class CustomProgressDialog extends Dialog {

     CustomProgressDialogBinding binding;
    public CustomProgressDialog(CustomDialogModel customDialogModel) {
        super(customDialogModel.getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_progress_dialog, null, false);
        setContentView(binding.getRoot());
    }


}
