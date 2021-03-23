package com.auro.scholr.home.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.databinding.PassportSpinnerItemBinding;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;

import java.util.List;

public class  PassportSpinnerAdapter extends BaseAdapter {
    PassportSpinnerItemBinding binding;
    List<MonthDataModel> list;

    public PassportSpinnerAdapter() {

    }

    public PassportSpinnerAdapter(List<MonthDataModel> list) {
        this.list = list;
    }

    public void  setDataList(List<MonthDataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.passport_spinner_item, parent, false);
        binding.parentLayout.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.button_bg_dark_blue));
        binding.titleText.setText(list.get(position).getMonth());
        binding.dropDownIcon.setVisibility(View.GONE);
        return binding.getRoot();
    }
}

