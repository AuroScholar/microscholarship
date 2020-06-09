package com.auro.scholr.teacher.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.databinding.TeacherDocumentItemLayoutBinding;
import com.auro.scholr.teacher.data.model.TeacherDocumentModel;

public class TeacherDoumentViewHolder  extends RecyclerView.ViewHolder {

    TeacherDocumentItemLayoutBinding binding;


    public TeacherDoumentViewHolder(@NonNull TeacherDocumentItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void bindUser(TeacherDocumentModel model, int position) {
        binding.txtDocumentName.setText(model.getUploadDocumentname());

    }
}
