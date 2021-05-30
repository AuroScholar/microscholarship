package com.auro.scholr.home.presentation.view.adapter.newuiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.CustomDialerItemLayoutBinding;
import com.auro.scholr.home.data.model.signupmodel.DialerModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class CustomDialerAdapter extends RecyclerView.Adapter<CustomDialerAdapter.ViewHolder> {

    List<DialerModel> mValues;
    Context mContext;
    CustomDialerItemLayoutBinding binding;
    CommonCallBackListner commonCallBackListner;

    public CustomDialerAdapter() {

    }

    public CustomDialerAdapter(Context context, List<DialerModel> values, CommonCallBackListner commonCallBackListner) {
        mValues = values;
        mContext = context;
        this.commonCallBackListner = commonCallBackListner;
    }

    public void updateList(ArrayList<DialerModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomDialerItemLayoutBinding binding;

        public ViewHolder(CustomDialerItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(DialerModel resModel, int position) {
            if (position == 9 || position == 11) {
                binding.numberText.setVisibility(View.INVISIBLE);
            } else {
                binding.numberText.setVisibility(View.VISIBLE);
                binding.numberText.setText(resModel.getNumber());
            }
            if (position == 11) {
                binding.numberDelete.setVisibility(View.VISIBLE);
            } else {
                binding.numberDelete.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.custom_dialer_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.setData(mValues.get(position), position);
        DialerModel dialerModel = mValues.get(position);
        if (position != 9) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppLogger.e("CustomDialerAdapter-", "pos----" + position);
                    if (commonCallBackListner != null) {
                        if (position == 11) {
                            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.CLEAR_EDIT_TEXT, dialerModel));

                        } else {
                            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.DIALER_CALL_BACK_LISTNER, dialerModel));

                        }
                    }


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
