package com.auro.scholr.teacher.presentation.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.databinding.SendMessageItemLayoutBinding;
import com.auro.scholr.home.data.model.SelectResponseModel;
import com.auro.scholr.home.data.model.TeacherDocumentModel;

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
