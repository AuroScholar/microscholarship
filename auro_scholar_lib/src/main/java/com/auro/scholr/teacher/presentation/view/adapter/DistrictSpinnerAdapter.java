package com.auro.scholr.teacher.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.databinding.StateSpinnerItemBinding;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;

import java.util.List;

public class DistrictSpinnerAdapter extends BaseAdapter {
    StateSpinnerItemBinding binding;
    List<DistrictDataModel> list;

    LayoutInflater inflter;;

    public DistrictSpinnerAdapter(List<DistrictDataModel> list) {
        inflter = (LayoutInflater.from(AuroApp.getAppContext().getApplicationContext()));
        this.list = list;
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
                R.layout.state_spinner_item, parent, false);
        binding.stateTitle.setText(list.get(position).getDistrict_name());
        return binding.getRoot();
    }
}
