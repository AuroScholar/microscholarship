package com.auro.scholr.teacher.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.SelectAppointmentItemLayoutBinding;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.DateUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SelectAppointmentAdapter extends RecyclerView.Adapter {

    List<String> list;
    CommonCallBackListner commonCallBackListner;
    private int checkedPosition = 0;

    public SelectAppointmentAdapter(List<String> list, CommonCallBackListner commonCallBackListner) {
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        SelectAppointmentItemLayoutBinding selectAppointmentItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.select_appointment_item_layout, viewGroup, false);
        return new SelectAppointmentViewHolder(selectAppointmentItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((SelectAppointmentViewHolder) holder).bindUser(list.get(position), position, commonCallBackListner);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SelectAppointmentViewHolder extends RecyclerView.ViewHolder {

        SelectAppointmentItemLayoutBinding binding;

        public SelectAppointmentViewHolder(@NonNull SelectAppointmentItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindUser(String model, int position, CommonCallBackListner commonCallBackListner) {

            binding.msgText.setText(DateUtil.convertDateFormat(AppConstant.DateFormats.DD_MMM_HH_MM_AA, AppConstant.DateFormats.DD_MM_YY_HH_MM, model));

            if (checkedPosition == getAdapterPosition()) {
                binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
            } else {
                binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.circle_outline));

            }

            binding.llayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (commonCallBackListner != null) {

                        binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
                        if (checkedPosition != getAdapterPosition()) {
                            notifyItemChanged(checkedPosition);
                            checkedPosition = getAdapterPosition();
                        }
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.GET_ZOHO_APPOINTMENT, model));
                    }
                }
            });

        }
    }

}
