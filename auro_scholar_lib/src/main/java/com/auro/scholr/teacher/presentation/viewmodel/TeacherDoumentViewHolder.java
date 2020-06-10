package com.auro.scholr.teacher.presentation.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.TeacherDocumentItemLayoutBinding;
import com.auro.scholr.teacher.data.model.TeacherDocumentModel;
import com.auro.scholr.util.AppUtil;

public class TeacherDoumentViewHolder extends RecyclerView.ViewHolder {

    TeacherDocumentItemLayoutBinding binding;


    public TeacherDoumentViewHolder(@NonNull TeacherDocumentItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(TeacherDocumentModel model, int position, CommonCallBackListner commonCallBackListner) {
        binding.txtDocumentName.setText(model.getUploadDocumentname());
        binding.docImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.DOCUMENT_CLICK,model));
                }

            }
        });

    }
}
