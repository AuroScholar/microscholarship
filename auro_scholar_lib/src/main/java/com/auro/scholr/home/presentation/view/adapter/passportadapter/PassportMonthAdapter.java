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
import com.auro.scholr.databinding.PassportItemLayoutBinding;
import com.auro.scholr.home.data.model.passportmodels.PassportMonthModel;

import java.util.ArrayList;
import java.util.List;


public class PassportMonthAdapter extends RecyclerView.Adapter<PassportMonthAdapter.ViewHolder> {

    List<PassportMonthModel> mValues;
    Context mContext;
    PassportItemLayoutBinding binding;
    CommonCallBackListner listner;

    public PassportMonthAdapter(Context context, List<PassportMonthModel> values, CommonCallBackListner listner) {
        mValues = values;
        mContext = context;
        this.listner = listner;
    }

    public void updateList(ArrayList<PassportMonthModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        PassportItemLayoutBinding binding;

        public ViewHolder(PassportItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(PassportMonthModel resModel, int position) {
            binding.monthTitle.setText(resModel.getMonthName());
            if (resModel.isExpanded()) {
                binding.downArrow.setRotation(180);
                binding.subjectList.setVisibility(View.VISIBLE);
            } else {
                binding.downArrow.setRotation(0);
                binding.subjectList.setVisibility(View.GONE);
            }
            binding.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStatusList(position, resModel);
                }
            });

        }

    }

    public void updateStatusList(int position, PassportMonthModel resModel) {
        for (int i = 0; i < mValues.size(); i++) {
            if (position == i) {
                if (resModel.isExpanded()) {
                    mValues.get(i).setExpanded(false);
                } else {
                    mValues.get(i).setExpanded(true);
                }
            } else {
                mValues.get(i).setExpanded(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.passport_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position), position);

        setSubjectAdapter(position);
      /*  Vholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.commonEventListner(AppUtil.getCommonClickModel(1, Status.ITEM_CLICK, mValues.get(position)));
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void updateData() {

    }

    public void setSubjectAdapter(int position) {
        binding.subjectList.setLayoutManager(new LinearLayoutManager(mContext));
        binding.subjectList.setHasFixedSize(true);
        binding.subjectList.setNestedScrollingEnabled(false);
        PassportSubjectAdapter passportSpinnerAdapter = new PassportSubjectAdapter(mContext, mValues.get(position).getPassportSubjectModelList(), null);
        binding.subjectList.setAdapter(passportSpinnerAdapter);
    }

}
