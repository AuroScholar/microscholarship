package com.auro.scholr.teacher.presentation.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.SendMessageItemLayoutBinding;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.util.AppUtil;

public class SelectMessageViewHolder extends RecyclerView.ViewHolder {

    SendMessageItemLayoutBinding binding;

    public SelectMessageViewHolder(@NonNull SendMessageItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(SelectResponseModel model, int position, CommonCallBackListner commonCallBackListner) {
        binding.msgText.setText(model.getMessage());

        if (model.isCheck()) {
            binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
        } else {
            binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.circle_auro_outline));

        }
        binding.llayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.MESSAGE_SELECT_CLICK, model));
                }
            }
        });


    }
}
