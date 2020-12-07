package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.FriendRequestItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendRequestList;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FriendRequestListAdapter extends RecyclerView.Adapter<FriendRequestListAdapter.ViewHolder> {

    List<FriendRequestList.Friends> mValues;
    Context mContext;
    FriendRequestItemLayoutBinding binding;
    int count;

    CommonCallBackListner commonCallBackListner;

    public FriendRequestListAdapter(Context context, List<FriendRequestList.Friends> values, int count, CommonCallBackListner commonCallBackListner) {
        mValues = values;
        mContext = context;
        this.count = count;
        this.commonCallBackListner = commonCallBackListner;
    }

    public void updateList(ArrayList<FriendRequestList.Friends> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        FriendRequestItemLayoutBinding binding;

        public ViewHolder(FriendRequestItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(FriendRequestList.Friends resModel, int position) {
            binding.nameText.setText(resModel.getStudent_name());
            ImageUtil.loadCircleImage(binding.profileImage, resModel.getStudent_photo());

            binding.tvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                            if (commonCallBackListner != null) {
                                // list.get(position).setSubjectPos(subjectPos);
                                commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(Integer.valueOf(resModel.getFriend_request_id()), Status.REQUEST_ACCEPT, resModel.getRegistration_id()));
                            }

                }
            });
            binding.tvDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (commonCallBackListner != null) {
                        // list.get(position).setSubjectPos(subjectPos);
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(Integer.valueOf(resModel.getFriend_request_id()), Status.REQUEST_DECLINE, resModel.getRegistration_id()));
                    }

                }
            });
        }

    }

    @Override
    public FriendRequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.friend_request_item_layout, viewGroup, false);
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
