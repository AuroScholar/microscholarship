package com.auro.scholr.util.disclaimer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.AmParentDialogBinding;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.util.ViewUtil;


public class DisclaimerMoneyTransferDialog extends Dialog {

    public Activity activity;
    private AmParentDialogBinding binding;
    PrefModel prefModel;

    public DisclaimerMoneyTransferDialog(Activity context) {
        super(context);
        this.activity = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.am_parent_dialog, null, false);
        setContentView(binding.getRoot());
        prefModel = AppPref.INSTANCE.getModelInstance();
        binding.tvParent.setVisibility(View.VISIBLE);
        binding.tvMessage.setText("I hereby submit voluntarily at my/our own discretion, the bank account details and the Aadhar card for the purpose of transferring micro-scholarships which will be received to my account. \n");
        binding.RlKycCheck.setVisibility(View.GONE);
        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefModel.isPreMoneyTransferDisclaimer()) {
                    dismiss();
                    ((StudentMainDashboardActivity) activity).popBackStack();
                }
            }
        });

        binding.RPAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkIsParent.isChecked()) {
                    dismiss();
                    prefModel.setPreMoneyTransferDisclaimer(false);
                    AppPref.INSTANCE.setPref(prefModel);
                } else {
                    ViewUtil.showSnackBar(binding.getRoot(), "Please select checkbox");
                }


            }
        });
    }
}