package com.auro.scholr.util.disclaimer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.LoginDiscalimerDialogLayoutBinding;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.snippety.core.Snippety;
import com.auro.scholr.util.snippety.core.Truss;


public class QuizDisclaimerDialog extends Dialog {

    public Activity activity;
    private LoginDiscalimerDialogLayoutBinding binding;
    PrefModel prefModel;
    CommonCallBackListner commonCallBackListner;

    public QuizDisclaimerDialog(Activity context,CommonCallBackListner listner) {
        super(context);
        this.activity = context;
        this.commonCallBackListner=listner;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.login_discalimer_dialog_layout, null, false);
        setContentView(binding.getRoot());
        setText();
        prefModel = AppPref.INSTANCE.getModelInstance();
        binding.closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.RPAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                prefModel.setPreQuizDisclaimer(false);
                AppPref.INSTANCE.setPref(prefModel);
                if(commonCallBackListner!=null)
                {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.ACCEPT_PARENT_BUTTON,""));
                }

            }
        });
    }


    private void setText() {

        binding.RPAccept.setText("Accept and start quiz");

        int leadWidth = activity.getResources().getDimensionPixelOffset(R.dimen.space_medium);
        int gapWidth = activity.getResources().getDimensionPixelOffset(R.dimen.space_xlarge);
        leadWidth = 0;
        binding.tvMessage.setText(new Truss()
                .appendln("I/we hereby accept the allowance of the Auro Scholar application being used by our child and grant our consent to take pictures in the quiz for proctoring/invigilation purposes. Auro Scholar inherits the confidentiality of the information provided.", new Snippety().bullet(leadWidth, gapWidth))
                .appendln()
                .build());
    }
    /* .appendln("Parent will not help the student in quiz", new Snippety().bullet(leadWidth, gapWidth))
                .appendln()
                .appendln("Money will be transferred to the parent", new Snippety().bullet(leadWidth, gapWidth))*/

}