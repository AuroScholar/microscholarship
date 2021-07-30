package com.auro.scholr.util.disclaimer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.PaymentOtpDialogBinding;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.timer.Hourglass;


public class CustomOtpDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    private PaymentOtpDialogBinding binding;
    PrefModel prefModel;
    String otptext = "";
    Hourglass timer;
    String TAG = "CustomOtpDialog";

    public CustomOtpDialog(Activity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.payment_otp_dialog, null, false);
        setContentView(binding.getRoot());

        prefModel = AppPref.INSTANCE.getModelInstance();
        binding.mobileNumberText.setText(prefModel.getUserMobile());
        binding.RPVerify.setOnClickListener(this);
        binding.closeButton.setOnClickListener(this);
        initRecordingTimer();
        binding.otpView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AppLogger.d(TAG, "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
                otptext = s.toString();
                if (!TextUtil.isEmpty(otptext) && otptext.length() == 6) {
                    ((StudentMainDashboardActivity) activity).verifyOtpRxApi(otptext);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void showProgress() {
        binding.RPVerify.setVisibility(View.GONE);
        binding.progressBar2.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        binding.RPVerify.setVisibility(View.GONE);
        binding.progressBar2.setVisibility(View.GONE);
        binding.otpView.setText("");
    }

    public void showSnackBar(String msg) {
        ViewUtil.showSnackBar(binding.getRoot(), msg);
    }


    public void initRecordingTimer() {
        binding.resendTimerTxt.setVisibility(View.VISIBLE);
        timer = new Hourglass() {
            @Override
            public void onTimerTick(final long timeRemaining) {
                int seconds = (int) (timeRemaining / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                binding.resendTimerTxt.setText(activity.getString(R.string.you_can_resend_in) + " " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onTimerFinish() {
                binding.RPVerify.setVisibility(View.VISIBLE);
                binding.resendTimerTxt.setVisibility(View.GONE);
            }
        };
        timer.setTime(120 * 1000 + 1000);
        timer.startTimer();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resend_timer_txt) {
            ((StudentMainDashboardActivity) activity).sendOtpApiReqPass();
            initRecordingTimer();
        } else if (v.getId() == R.id.RPVerify) {
            dismiss();
            ((StudentMainDashboardActivity) activity).sendOtpApiReqPass();
        } else if (v.getId() == R.id.closeButton) {
            dismiss();
        }
    }
}