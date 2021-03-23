package com.auro.scholr.home.presentation.view.adapter.passportadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.PassportQuizItemLayoutBinding;
import com.auro.scholr.home.data.model.passportmodels.PassportQuizGridModel;
import com.auro.scholr.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class PassportQuizDetailAdapter extends RecyclerView.Adapter<PassportQuizDetailAdapter.ViewHolder> {

    List<PassportQuizGridModel> mValues;
    Context mContext;
    PassportQuizItemLayoutBinding binding;
    CommonCallBackListner listner;

    public PassportQuizDetailAdapter(Context context, List<PassportQuizGridModel> values, CommonCallBackListner listner) {
        mValues = values;
        mContext = context;
        this.listner = listner;
    }

    public void updateList(ArrayList<PassportQuizGridModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        PassportQuizItemLayoutBinding binding;

        public ViewHolder(PassportQuizItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.passport_quiz_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        //AppLogger.e("TransactionsFragment","Quiz detail Adpater size-"+mValues.size());
       // AppLogger.e("TransactionsFragment","Quiz detail Adpater size-"+mValues.get(position).getQuizData());
        //   Vholder.setData(mValues.get(position), position);
        binding.quizTitle.setText(mValues.get(position).getQuizHead());
        binding.quizData.setText(mValues.get(position).getQuizData());
        binding.quizData.setTextColor(mValues.get(position).getQuizColor());
        binding.quizIcon.setImageResource(mValues.get(position).getQuizImagePath());
        Vholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.commonEventListner(AppUtil.getCommonClickModel(1, Status.ITEM_CLICK, mValues.get(position)));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void updateData() {

    }

}
