package com.auro.scholr.home.presentation.view.adapter.passportadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.PassportQuizDetailLayoutBinding;
import com.auro.scholr.home.data.model.passportmodels.PassportQuizDetailModel;
import com.auro.scholr.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class PassportQuizSubDetailAdapter extends RecyclerView.Adapter<PassportQuizSubDetailAdapter.ViewHolder> {

    List<PassportQuizDetailModel> mValues;
    Context mContext;
    PassportQuizDetailLayoutBinding binding;
    CommonCallBackListner listner;

    public PassportQuizSubDetailAdapter(Context context, List<PassportQuizDetailModel> values, CommonCallBackListner listner) {
        mValues = values;
        mContext = context;
        this.listner = listner;
    }

    public void updateList(ArrayList<PassportQuizDetailModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        PassportQuizDetailLayoutBinding binding;

        public ViewHolder(PassportQuizDetailLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.passport_quiz_detail_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
     //   Vholder.setData(mValues.get(position), position);
      //  binding.topicName.setText(mValues.get(position).getTopicName());
      //  binding.topicNumber.setText(""+position);
        setQuizAdapter(position);
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



    public void setQuizAdapter(int position) {
        //AppLogger.e("TransactionsFragment","Quiz sub detail Adpater size-"+mValues.size());
        binding.gridRecycler.setLayoutManager(new GridLayoutManager(mContext,2));
        binding.gridRecycler.setHasFixedSize(true);
        binding.gridRecycler.setNestedScrollingEnabled(false);
        PassportQuizDetailAdapter passportSpinnerAdapter = new PassportQuizDetailAdapter(mContext,mValues.get(position).getPassportQuizGridModelList(),null);
        binding.gridRecycler.setAdapter(passportSpinnerAdapter);
    }
}
