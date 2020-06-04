package com.auro.scholr.home.presentation.view.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.databinding.MonthWiseItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.MonthlyScholarShipModel;

public class MonthlyWiseViewHolder extends RecyclerView.ViewHolder {
    MonthWiseItemLayoutBinding binding;


    public MonthlyWiseViewHolder(@NonNull MonthWiseItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void bindUser(MonthlyScholarShipModel model, int position) {

    }
}
