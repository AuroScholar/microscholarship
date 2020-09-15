package com.auro.scholr.teacher.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.databinding.TeacherProgressItemLayoutBinding;
import com.auro.scholr.teacher.data.model.response.TeacherProgressStatusModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherProgressAdapter extends RecyclerView.Adapter {

    List<TeacherProgressStatusModel> list;
    CommonCallBackListner commonCallBackListner;

    public TeacherProgressAdapter(List<TeacherProgressStatusModel> list, CommonCallBackListner commonCallBackListner) {

        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    public void updateList(ArrayList<TeacherProgressStatusModel> values) {
        this.list = values;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        TeacherProgressItemLayoutBinding teacherProgressItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.teacher_progress_item_layout, viewGroup, false);
        return new TeacherProgressViewHolder(teacherProgressItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        list.get(0).setStatus(false);
//        list.get(2).setStatus(false);
//        list.get(0).setStatus(false);
//        list.get(3).setStatus(false);
        ((TeacherProgressViewHolder) holder).bindUser(list, position, commonCallBackListner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
