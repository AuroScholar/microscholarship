package com.auro.scholr.home.presentation.view.adapter.newuiadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.databinding.ClassItemLayoutBinding;
import com.auro.scholr.home.data.model.CategorySubjectResModel;
import com.auro.scholr.util.AppLogger;

import java.util.List;

public class SubjectPrefProfileAdapter extends RecyclerView.Adapter<SubjectPrefProfileAdapter.SubjectPrefHolder> {

    List<CategorySubjectResModel> list;
    CommonCallBackListner commonCallBackListner;

    public SubjectPrefProfileAdapter(List<CategorySubjectResModel> list, CommonCallBackListner commonCallBackListner) {
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    @NonNull
    @Override
    public SubjectPrefHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ClassItemLayoutBinding classItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.class_item_layout, viewGroup, false);
        return new SubjectPrefHolder(classItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectPrefHolder holder, int index) {
        CategorySubjectResModel resModel = list.get(index);
        AppLogger.e("onBindViewHolder --", "step 0--" + resModel.isLock());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resModel.isLock()) {
                    AppLogger.e("onBindViewHolder --", "step 1--" + resModel.isLock());
                    updateList(index);
                }
            }
        });

        holder.bindUser(list.get(index), index, commonCallBackListner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectPrefHolder extends RecyclerView.ViewHolder {
        ClassItemLayoutBinding binding;

        public SubjectPrefHolder(@NonNull ClassItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bindUser(CategorySubjectResModel model, int position, CommonCallBackListner commonCallBackListner) {
            binding.txtClass.setText(model.getSubjectname());
            if(!model.isSelected()) {
                binding.txtClass.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.color_light_grey));
                binding.buttonClick.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.class_disable_border_background));
            }

        }
    }

    public void setData(List<CategorySubjectResModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    void updateList(int index) {

    }

}