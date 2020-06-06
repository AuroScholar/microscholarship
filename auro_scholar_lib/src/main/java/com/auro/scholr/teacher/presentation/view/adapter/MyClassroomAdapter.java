package com.auro.scholr.teacher.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.QuizDocUploadLayoutBinding;
import com.auro.scholr.databinding.StudentClassItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class MyClassroomAdapter extends RecyclerView.Adapter<MyClassroomAdapter.ViewHolder> {

    List<FriendsLeaderBoardModel> mValues;
    Context mContext;
    StudentClassItemLayoutBinding binding;
    CommonCallBackListner commonCallBackListner;
    boolean progressStatus;

    public MyClassroomAdapter(Context context, List<FriendsLeaderBoardModel> values, CommonCallBackListner commonCallBackListner) {
        this.commonCallBackListner = commonCallBackListner;
        mValues = values;
        mContext = context;
    }

    public void updateList(ArrayList<FriendsLeaderBoardModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public void updateProgressValue(boolean progressStatus) {
        this.progressStatus = progressStatus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StudentClassItemLayoutBinding binding;

        public ViewHolder(StudentClassItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(FriendsLeaderBoardModel kycDocumentDatamodel, int position) {
            ImageUtil.loadCircleImage(binding.profileImage, (String) kycDocumentDatamodel.getImagePath());
            binding.numberText.setText("" +( position + 1)+".");
        }

    }

    @Override
    public MyClassroomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.student_class_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position), position);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
