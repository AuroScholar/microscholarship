package com.auro.scholr.util.alert_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.ExitSelectionLayoutBinding;
import com.auro.scholr.util.AppUtil;


public class ExitDialog extends Dialog implements View.OnClickListener {

    public Activity context;
    private ExitSelectionLayoutBinding binding;
    String msg;

    public ExitDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }
    public ExitDialog(@NonNull Activity context, String msg) {
        super(context);
        this.context = context;
        this.msg=msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),  R.layout.exit_selection_layout, null, false);
        //binding.tvMessage.setText("What App is not install!!\nPlease Install App From PlayStore");
        binding.tvMessage.setText(msg);
        setContentView(binding.getRoot());
        setListener();
    }


    protected void setListener() {
        binding.btnYes.setOnClickListener(this);
    }


    protected int getLayout() {
        return R.layout.exit_selection_layout;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yes) {
            dismiss();
            if (AppUtil.callBackListner != null) {
                AppUtil.callBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.EXIT_DIALOG_CLICK,""));
            }
        }
    }


}
