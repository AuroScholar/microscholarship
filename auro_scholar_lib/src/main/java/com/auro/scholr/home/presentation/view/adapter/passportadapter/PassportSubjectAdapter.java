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
import com.auro.scholr.home.data.model.passportmodels.PassportSubjectModel;

import java.util.ArrayList;
import java.util.List;


public class PassportSubjectAdapter extends RecyclerView.Adapter<PassportSubjectAdapter.ViewHolder> {

    List<PassportSubjectModel> mValues;
    Context mContext;
    PassportItemLayoutBinding binding;
    CommonCallBackListner listner;

    public PassportSubjectAdapter(Context context, List<PassportSubjectModel> values, CommonCallBackListner listner) {
        mValues = values;
        mContext = context;
        this.listner = listner;
    }

    public void updateList(ArrayList<PassportSubjectModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        PassportItemLayoutBinding binding;

        public ViewHolder(PassportItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(PassportSubjectModel resModel, int position) {
            binding.monthTitle.setText(resModel.getSubjectName());

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.passport_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
       Vholder.setData(mValues.get(position), position);
        binding.parentLayout.setBackground(mContext.getResources().getDrawable(R.drawable.button_bg_grey));
        setQuizAdapter(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void updateData() {

    }



    public void setQuizAdapter(int position) {
        binding.subjectList.setLayoutManager(new LinearLayoutManager(mContext));
        binding.subjectList.setHasFixedSize(true);
        binding.subjectList.setNestedScrollingEnabled(false);
        PassportQuizAdapter passportSpinnerAdapter = new PassportQuizAdapter(mContext,mValues.get(position).getQuizData(),null);
        binding.subjectList.setAdapter(passportSpinnerAdapter);
    }


    public void updateStatusList(int position, PassportSubjectModel resModel) {
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

}
