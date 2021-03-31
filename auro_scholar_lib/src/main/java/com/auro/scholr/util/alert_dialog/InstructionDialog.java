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
import com.auro.scholr.databinding.InstructionDialogCustomBinding;
import com.auro.scholr.util.AppUtil;

public class InstructionDialog extends Dialog  {

    public Context context;
    private InstructionDialogCustomBinding binding;

    public InstructionDialog(Context context) {
        super(context);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.instruction_dialog_custom, null, false);
        setContentView(binding.getRoot());

    }



}