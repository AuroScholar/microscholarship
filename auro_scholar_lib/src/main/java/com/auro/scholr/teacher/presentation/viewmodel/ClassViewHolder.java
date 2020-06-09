package com.auro.scholr.teacher.presentation.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.databinding.ClassItemLayoutBinding;
import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.teacher.presentation.view.adapter.DistrictSpinnerAdapter;

import java.util.List;


public class ClassViewHolder extends RecyclerView.ViewHolder  {

    ClassItemLayoutBinding binding;

    public ClassViewHolder(@NonNull ClassItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    public void bindUser(SelectClassesSubject model, int position, Context mcontext) {

        binding.txtClass.setText(model.getText());

        binding.buttonClick.setBackground(model.isSelected() ? ContextCompat.getDrawable(mcontext,R.drawable.class_bule_border_background) : ContextCompat.getDrawable(mcontext,R.drawable.class_borader_background));
        binding.txtClass.setTextColor(model.isSelected() ? ContextCompat.getColor(mcontext,R.color.white): ContextCompat.getColor(mcontext,R.color.grey_color));

        binding.buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.buttonClick) {
                    model.setSelected(!model.isSelected());
                    binding.buttonClick.setBackground(model.isSelected() ? ContextCompat.getDrawable(mcontext,R.drawable.class_bule_border_background) : ContextCompat.getDrawable(mcontext,R.drawable.class_borader_background));
                    binding.txtClass.setTextColor(model.isSelected() ? ContextCompat.getColor(mcontext,R.color.white): ContextCompat.getColor(mcontext,R.color.grey_color));
                }
            }
        });
    }



}
