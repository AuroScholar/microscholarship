package com.auro.scholr.teacher.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.databinding.TeacherDocumentItemLayoutBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherDoumentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TeacherKycDocumentAdapter extends RecyclerView.Adapter {


    List<KYCDocumentDatamodel> list;
    CommonCallBackListner commonCallBackListner;

    public TeacherKycDocumentAdapter(List<KYCDocumentDatamodel> list, CommonCallBackListner commonCallBackListner) {

        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }
    public void updateList(ArrayList<KYCDocumentDatamodel> values) {
        this.list = values;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER:
                TeacherDocumentItemLayoutBinding teacherDocumentItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.teacher_document_item_layout, viewGroup, false);
                return new TeacherDoumentViewHolder(teacherDocumentItemLayoutBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = list.get(position).getViewType();

        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER:
                ((TeacherDoumentViewHolder) holder).bindUser(list.get(position), position,commonCallBackListner);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).getViewType()) {
            case AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER:
                return AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
