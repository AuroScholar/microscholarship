package com.auro.scholr.teacher.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.databinding.ClassItemLayoutBinding;

import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.teacher.presentation.viewmodel.ClassViewHolder;


import java.util.List;

public class ProfileScreenAdapter extends RecyclerView.Adapter {

    List<SelectClassesSubject> classlist;
    Context mcontext;


    public ProfileScreenAdapter(List<SelectClassesSubject> classlist,Context mcontext) {
        this.classlist = classlist;
        this.mcontext= mcontext;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.SUBJECTADAPTER:
                ClassItemLayoutBinding  ClassItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.class_item_layout, viewGroup, false);
                return new ClassViewHolder(ClassItemLayoutBinding);

            case AppConstant.FriendsLeaderBoard.CLASSESADAPTER:
                ClassItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.class_item_layout, viewGroup, false);
                return new ClassViewHolder(binding);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        int viewType = classlist.get(position).getViewType();

        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.SUBJECTADAPTER:
                ((ClassViewHolder) holder).bindUser(classlist.get(position), position, mcontext);
                break;

            case AppConstant.FriendsLeaderBoard.CLASSESADAPTER:
                ((ClassViewHolder) holder).bindUser(classlist.get(position),position,mcontext);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (classlist.get(position).getViewType()) {
            case AppConstant.FriendsLeaderBoard.SUBJECTADAPTER:
                return AppConstant.FriendsLeaderBoard.SUBJECTADAPTER;

            case AppConstant.FriendsLeaderBoard.CLASSESADAPTER:
                return AppConstant.FriendsLeaderBoard.CLASSESADAPTER;

            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return classlist.size();
    }
}
