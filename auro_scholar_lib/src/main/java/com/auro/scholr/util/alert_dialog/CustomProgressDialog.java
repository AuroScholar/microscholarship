package com.auro.scholr.util.alert_dialog;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.databinding.CustomKycDialogBinding;
import com.auro.scholr.databinding.CustomProgressDialogBinding;
import com.auro.scholr.databinding.DialogTeacherSelectYourMessageBinding;
import com.google.firebase.abt.component.AbtRegistrar;


public class CustomProgressDialog extends Dialog {

    CustomProgressDialogBinding binding;
    CustomKycDialogBinding customKycDialogBinding;
    CustomDialogModel customDialogModel;
    ButtonClickCallback callback;

    public CustomProgressDialog(CustomDialogModel customDialogModel) {
        super(customDialogModel.getContext());
        this.customDialogModel = customDialogModel;
    }

    public void setTitle(String msg) {
        customKycDialogBinding.cpTitle.setText(msg);
    }

    public void setProgressDialogText(String msg) {
        binding.cpTitle.setText(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!customDialogModel.isTwoButtonRequired()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_progress_dialog, null, false);
            setContentView(binding.getRoot());
        } else {
            customKycDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_kyc_dialog, null, false);
            customKycDialogBinding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.retryCallback();
                    }
                }
            });

            customKycDialogBinding.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.closeCallback();
                    }
                    dismiss();
                }
            });
            setContentView(customKycDialogBinding.getRoot());
        }

    }


    public interface ButtonClickCallback {
        void retryCallback();

        void closeCallback();
    }

    public void setCallcack(ButtonClickCallback callback) {
        this.callback = callback;
    }


    public void updateUI(int status) {
        switch (status) {
            case 0:
                customKycDialogBinding.cpPbar.setVisibility(View.GONE);
                customKycDialogBinding.buttonLayout.setVisibility(View.VISIBLE);
                customKycDialogBinding.button.setText("Retry");
                customKycDialogBinding.closeButton.setVisibility(View.VISIBLE);
                setTitle("Uploading Failed!");
                break;
            case 1:
                customKycDialogBinding.cpPbar.setVisibility(View.VISIBLE);
                customKycDialogBinding.closeButton.setVisibility(View.GONE);
                customKycDialogBinding.buttonLayout.setVisibility(View.GONE);
                setTitle(customDialogModel.getTitle());
                break;
        }

    }

    public void updateDataUi(int status) {
        switch (status) {
            case 0:
                customKycDialogBinding.cpPbar.setVisibility(View.VISIBLE);
                customKycDialogBinding.closeButton.setVisibility(View.GONE);
                customKycDialogBinding.buttonLayout.setVisibility(View.GONE);
                setTitle(customDialogModel.getTitle());
                break;
        }

    }
}
