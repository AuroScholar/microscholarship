package com.auro.scholr.teacher.presentation.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.TeacherDocumentItemLayoutBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.util.AppUtil;

public class TeacherDoumentViewHolder extends RecyclerView.ViewHolder {

    TeacherDocumentItemLayoutBinding binding;


    public TeacherDoumentViewHolder(@NonNull TeacherDocumentItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(KYCDocumentDatamodel model, int position, CommonCallBackListner commonCallBackListner) {
        binding.txtDocumentName.setText(model.getDocumentName());
        if (!model.getDocumentFileName().equalsIgnoreCase(AuroApp.getAppContext().getResources().getString(R.string.no_file_chosen))) {
            binding.txtFileName.setVisibility(View.VISIBLE);
            binding.txtFileName.setText(model.getDocumentFileName());
            binding.docImg.setOnClickListener(null);
        } else {
            binding.txtFileName.setVisibility(View.GONE);
        }
        if(position ==0 || position == 1){
            binding.txtDocumenthint.setText("Ex. Aadhaar,Pan card,Driving licence,etc.");
            binding.txtDocumenthint.setVisibility(View.VISIBLE);

        }else{
            binding.txtDocumenthint.setVisibility(View.GONE);
        }
        if (model.isModify()) {
            binding.docImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
        } else {
            binding.docImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_upload));
            binding.docImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.DOCUMENT_CLICK, model));
                    }

                }
            });
        }


    }
}
