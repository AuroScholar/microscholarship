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
import com.auro.scholr.databinding.LoginDiscalimerDialogLayoutBinding;
import com.auro.scholr.util.snippety.core.Snippety;
import com.auro.scholr.util.snippety.core.Truss;


public class LoginDisclaimerDialog extends Dialog {

    public Activity activity;
    private LoginDiscalimerDialogLayoutBinding binding;
    PrefModel prefModel;

    public LoginDisclaimerDialog(Activity context) {
        super(context);
        this.activity = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.login_discalimer_dialog_layout, null, false);
        setContentView(binding.getRoot());
        setText();
        prefModel = AppPref.INSTANCE.getModelInstance();
        binding.closeBt.setVisibility(View.GONE);
        binding.closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefModel.isPreLoginDisclaimer()) {
                    dismiss();
                } else {
                    activity.finishAffinity();
                }
            }
        });

        binding.RPAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                prefModel = AppPref.INSTANCE.getModelInstance();
                prefModel.setPreLoginDisclaimer(true);
                AppPref.INSTANCE.setPref(prefModel);
            }
        });
    }


    private void setText() {

        int leadWidth = activity.getResources().getDimensionPixelOffset(R.dimen.space_medium);
        int gapWidth = activity.getResources().getDimensionPixelOffset(R.dimen.space_xlarge);
        leadWidth = 0;
        binding.tvMessage.setText(new Truss()
                .appendln("I/we hereby allow my/our child to use the Auro Scholar application to take quizzes, learn and win scholarships. In consideration of this, I will not allow, promote or use any unfair means to help my child to get scholarships or while taking quizzes.", new Snippety().bullet(leadWidth, gapWidth))
                .appendln()
                .appendln("I/We declare to provide all the demographic and personal information voluntarily furnished by my/our child is true, correct and complete and has been under my/our supervision and Auro Scholar is not liable for any incorrect information provided by me/us.", new Snippety().bullet(leadWidth, gapWidth))
                .appendln()
                .appendln("I grant my consent to Auro Scholar to use the details provided, The Scholarship shall be transferred to my bank account post their consent.", new Snippety().bullet(leadWidth, gapWidth))
                .appendln()
                .build());

    }

}