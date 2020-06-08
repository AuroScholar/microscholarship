package com.auro.scholr.teacher.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.databinding.ClassItemLayoutBinding;

import com.auro.scholr.teacher.data.model.ClassResponseModel;


public class ClassViewHolder extends RecyclerView.ViewHolder{

    ClassItemLayoutBinding binding;
    public ClassViewHolder(@NonNull ClassItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(ClassResponseModel model, int position) {
        binding.txtClass.setText(model.getClasses());
        // binding.txtDocumentName.setText(model.getUploadDocumentname());

    }
}
