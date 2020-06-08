package com.auro.scholr.teacher.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.databinding.SendMessageItemLayoutBinding;
import com.auro.scholr.teacher.data.model.SelectResponseModel;

public class SelectMessageViewHolder extends RecyclerView.ViewHolder {

    SendMessageItemLayoutBinding binding;
    public SelectMessageViewHolder(@NonNull SendMessageItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(SelectResponseModel model, int position) {
       // binding.txtDocumentName.setText(model.getUploadDocumentname());

    }
}
