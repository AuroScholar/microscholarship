package com.auro.scholr.home.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.databinding.MonthWiseItemLayoutBinding;
import com.auro.scholr.home.data.model.MonthlyScholarShipModel;
import com.auro.scholr.home.presentation.view.viewholder.MonthlyWiseViewHolder;

import java.util.List;

public class MontlyWiseAdapter  extends RecyclerView.Adapter {


    List<MonthlyScholarShipModel> listmonthly;

    public MontlyWiseAdapter(List<MonthlyScholarShipModel> listmonthly) {
        this.listmonthly=listmonthly;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER:
                MonthWiseItemLayoutBinding MonthWiseItemLayoutbinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.month_wise_item_layout, viewGroup, false);
                return new MonthlyWiseViewHolder(MonthWiseItemLayoutbinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = listmonthly.get(position).getViewType();

        switch (viewType) {
            case AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER:
                ((MonthlyWiseViewHolder) holder).bindUser(listmonthly.get(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (listmonthly.get(position).getViewType()) {
            case AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER:
                return AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return listmonthly.size();
    }
}
