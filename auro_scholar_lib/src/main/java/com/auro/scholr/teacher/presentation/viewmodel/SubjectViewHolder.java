package com.auro.scholr.teacher.presentation.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.ClassItemLayoutBinding;
import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.util.AppUtil;


public class SubjectViewHolder extends RecyclerView.ViewHolder {

    ClassItemLayoutBinding binding;

    public SubjectViewHolder(@NonNull ClassItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    public void bindUser(SelectClassesSubject model, int position, Context mcontext, CommonCallBackListner commonCallBackListner, int adapter) {

        binding.txtClass.setText(model.getText());
        binding.buttonClick.setBackground(model.isSelected() ? ContextCompat.getDrawable(mcontext, R.drawable.class_bule_border_background) : ContextCompat.getDrawable(mcontext, R.drawable.class_borader_background));
        binding.txtClass.setTextColor(model.isSelected() ? ContextCompat.getColor(mcontext, R.color.white) : ContextCompat.getColor(mcontext, R.color.grey_color));

        binding.buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }
                binding.buttonClick.setBackground(model.isSelected() ? ContextCompat.getDrawable(mcontext, R.drawable.class_bule_border_background) : ContextCompat.getDrawable(mcontext, R.drawable.class_borader_background));
                binding.txtClass.setTextColor(model.isSelected() ? ContextCompat.getColor(mcontext, R.color.white) : ContextCompat.getColor(mcontext, R.color.grey_color));
                setClickListnerForSubject(commonCallBackListner, model, adapter);

            }
        });
    }

    private void setClickListnerForSubject(CommonCallBackListner commonCallBackListner, SelectClassesSubject model, int subjectadapter) {
        if (commonCallBackListner != null) {
            if (subjectadapter == AppConstant.FriendsLeaderBoard.SUBJECTADAPTER) {
                commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER, Status.SUBJECT_CLICK, model));
            } else {
                commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(AppConstant.FriendsLeaderBoard.CLASSESADAPTER, Status.CLASS_CLICK, model));
            }
        }
    }

}
