package com.auro.scholr.util.alert_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.AskNameLayoutBinding;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;

public class AskNameCustomDialog  extends Dialog implements View.OnClickListener {

    public Context context;
    private String msg;
    private String tittle;
    private AskNameLayoutBinding binding;

    CommonCallBackListner commonCallBackListner;


    public AskNameCustomDialog(Context context, CustomDialogModel customDialogModel, CommonCallBackListner listner) {
        super(context);
        this.context = context;
        this.msg = customDialogModel.getContent();
        this.tittle = customDialogModel.getTitle();
        this.commonCallBackListner = listner;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.ask_name_layout, null, false);
        setContentView(binding.getRoot());
        //  binding.tvTitle.setText(tittle);
        binding.btDone.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btDone) {

            if(!TextUtil.isEmpty(binding.textName.getText().toString())) {
                dismiss();
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.NAME_DONE_CLICK, binding.textName.getText().toString()));
                }
            }else{
                binding.llinputPhone.setError("Please Enter Your Name");
            }
        }

    }


}